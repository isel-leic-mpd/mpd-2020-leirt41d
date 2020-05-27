package weather.dto;



public class SearchApiDto {

    private ResultDto result;

    public SearchApiDto(ResultDto result) {
        this.result = result;
    }
    public ResultDto getResult() {
        return result;
    }

}
