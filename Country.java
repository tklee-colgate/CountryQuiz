import java.util.ArrayList;

public class Country {
    private String capital;
    private int population;
    private String continent;
    private String language;

    //public static ArrayList<Country> dataList;

    public Country(String capital, int population, String continent, String language){
        this.capital=capital;
        this.population=population;
        this.continent=continent;
        this.language=language;
    }

    public String getCapital(){
        return this.capital;
    }

    public int getPopulation() {
        return this.population;
    }

    public String getContinent() {
        return this.continent;
    }

    public String getLanguage() {
        return this.language;
    }
}