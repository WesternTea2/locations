package locations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

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

    public List<LocationDto> getLocations() {
        Type targetListType = new TypeToken<List<LocationDto>>() {}.getType();
        return modelMapper.map(locations, targetListType);
    }
}
