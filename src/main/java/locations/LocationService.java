package locations;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocationService {

    private boolean isCapitalize;

    private LocationMapper locationMapper;

    private AtomicLong idGenerator = new AtomicLong();

    public LocationService(LocationMapper locationMapper, @Value("${locations.capitalize:false}") boolean isCapitalize) {
        this.locationMapper = locationMapper;
        this.isCapitalize = isCapitalize;
    }

    private List<Location> locations = new ArrayList<>(Arrays.asList(
            new Location(idGenerator.incrementAndGet(), "Budapest", 47.497912, 19.040235),
            new Location(idGenerator.incrementAndGet(), "BudaBuda", 47.497912, 19.040235),
            new Location(idGenerator.incrementAndGet(), "Catania", 41.497912, 14.040235),
            new Location(idGenerator.incrementAndGet(), "Taormina", 40.497912, 16.040235),
            new Location(idGenerator.incrementAndGet(), "Sao Paulo", 44.497912, 15.040235)
    ));

    public List<LocationDto> getLocations(QueryParameters queryParameters) {
        log.debug("Listing locations with prefix '{}' and suffix '{}'.",
                queryParameters.getPrefix(), queryParameters.getSuffix());
        List<Location> filtered = locations.stream()
                .filter( l -> queryParameters.getPrefix() == null || l.getName().toLowerCase().startsWith(queryParameters.getPrefix().toLowerCase()))
                .filter( l -> queryParameters.getSuffix() == null  || l.getName().toLowerCase().endsWith(queryParameters.getSuffix().toLowerCase()))
                .collect(Collectors.toList());
        log.debug("Found {} matching locations.", filtered.size());
        return locationMapper.toDto(filtered);
    }

    public LocationDto findLocationById(long id) {
        log.debug("Finding location by id {}.", id);
        return locationMapper.toDto(locations.stream()
                .filter(l -> l.getId() == id).findAny()
                .orElseThrow(notFoundException(id)));
    }

    public LocationDto createLocation(CreateLocationCommand createLocationCommand) {
        Location location = new Location(idGenerator.incrementAndGet(), normalizeName(createLocationCommand.getName()), createLocationCommand.getLatitude(), createLocationCommand.getLongitude());
        locations.add(location);
        log.info("Created location with id {} and name '{}'.", location.getId(), location.getName());
        return locationMapper.toDto(location);
    }

    public LocationDto updateLocation(long id, UpdateLocationCommand updateLocationCommand) {
        log.info("Updating location with id {}.", id);
        Location location = locations.stream()
                .filter(l -> l.getId() == id)
                .findFirst().orElseThrow(notFoundException(id));

        location.setName(normalizeName(updateLocationCommand.getName()));
        location.setLatitude(updateLocationCommand.getLatitude());
        location.setLongitude(updateLocationCommand.getLongitude());
        log.info("Updated location with id {}. New name is '{}'.", id, location.getName());

        return locationMapper.toDto(location);
    }

    public void deleteLocation(long id) {
        log.info("Deleting location with id {}.", id);
        Location location = locations.stream()
                .filter(l -> l.getId() == id)
                .findFirst().orElseThrow(notFoundException(id));

        locations.remove(location);
        log.info("Deleted location with id {}.", id);
    }

    public void deleteAllLocation() {
        log.info("Deleting all locations. Count before delete: {}.", locations.size());
        locations.clear();
        log.info("All locations deleted.");
    }

    private String normalizeName(String name) {
        String normalizedName = isCapitalize ? StringUtils.capitalize(name) : name;
        log.debug("Normalized location name from '{}' to '{}'.", name, normalizedName);
        return normalizedName;
    }

    private static Supplier<LocationNotFoundException> notFoundException(long id) {
        return () -> {
            log.warn("Location with id {} was not found.", id);
            return new LocationNotFoundException("Location with id " + id + " does not exist");
        };
    }
}
