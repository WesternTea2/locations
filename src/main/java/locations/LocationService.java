package locations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private ModelMapper modelMapper;

    public LocationService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private List<Location> locations = Arrays.asList(
            new Location(1L, "Budapest", 47.497912, 19.040235),
            new Location(2L, "Catania", 41.497912, 14.040235),
            new Location(3L,"Taormina", 40.497912, 14.040235)
    );

    public List<LocationDto> getLocations(Optional<String> prefix) {
        Type targetListType = new TypeToken<List<LocationDto>>() {}.getType();
        List<Location> filtered = locations.stream()
                .filter( l -> prefix.isEmpty() || l.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                .collect(Collectors.toList());
        return modelMapper.map(filtered, targetListType);
    }

    public LocationDto getLocationById(long id) {
        return modelMapper.map(locations.stream()
                .filter(l -> l.getId() == id).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Location with id " + id + " does not exist")),
                        LocationDto.class);
    }
}
