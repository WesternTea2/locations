package locations;

import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private LocationMapper locationMapper;

    private AtomicLong idGenerator = new AtomicLong();

    public LocationService(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    private List<Location> locations = new ArrayList<>(Arrays.asList(
            new Location(idGenerator.incrementAndGet(), "Budapest", 47.497912, 19.040235),
            new Location(idGenerator.incrementAndGet(), "BudaBuda", 47.497912, 19.040235),
            new Location(idGenerator.incrementAndGet(), "Catania", 41.497912, 14.040235),
            new Location(idGenerator.incrementAndGet(),"Taormina", 40.497912, 16.040235),
            new Location(idGenerator.incrementAndGet(),"Sao Paulo", 44.497912, 15.040235)
    ));

    public List<LocationDto> getLocations(QueryParameters queryParameters) {
        List<Location> filtered = locations.stream()
                .filter( l -> queryParameters.getPrefix() == null || l.getName().toLowerCase().startsWith(queryParameters.getPrefix().toLowerCase()))
                .filter( l -> queryParameters.getSuffix() == null  || l.getName().toLowerCase().endsWith(queryParameters.getSuffix().toLowerCase()))
                .collect(Collectors.toList());
        return locationMapper.toDto(filtered);
    }

    public LocationDto findLocationById(long id) {
        return locationMapper.toDto(locations.stream()
                .filter(l -> l.getId() == id).findAny()
                .orElseThrow(notFoundException(id)));
    }

    public LocationDto createLocation(CreateLocationCommand createLocationCommand) {
        Location location = new Location(idGenerator.incrementAndGet(), createLocationCommand.getName(), createLocationCommand.getLatitude(), createLocationCommand.getLongitude());
        locations.add(location);
        return locationMapper.toDto(location);
    }

    public LocationDto updateLocation(long id, UpdateLocationCommand updateLocationCommand) {
        Location location = locations.stream()
                .filter(l -> l.getId() == id)
                .findFirst().orElseThrow(notFoundException(id));

        location.setName(updateLocationCommand.getName());
        location.setLatitude(updateLocationCommand.getLatitude());
        location.setLongitude(updateLocationCommand.getLongitude());

        return locationMapper.toDto(location);
    }

    public void deleteLocation(long id) {
        Location location = locations.stream()
                .filter(l -> l.getId() == id)
                .findFirst().orElseThrow(notFoundException(id));

        locations.remove(location);
    }

    private static Supplier<LocationNotFoundException> notFoundException(long id) {
        return () -> new LocationNotFoundException("Location with id " + id + " does not exist");
    }

    public void deleteAllLocation() {
        locations.clear();
    }
}
