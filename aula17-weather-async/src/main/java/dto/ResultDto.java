package dto;

public class ResultDto {
    private LocationDto[] result;
    public ResultDto( LocationDto[] result) {
        this.result = result;
    }

    public LocationDto[] getLocations() {
        return result;
    }
}
