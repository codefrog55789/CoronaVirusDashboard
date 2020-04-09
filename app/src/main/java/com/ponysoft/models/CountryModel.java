package com.ponysoft.models;

public class CountryModel {
    public String country;
    public CountryInfoModel countryInfo;
    public long updated;
    public int cases;
    public int todayCases;
    public int deaths;
    public int todayDeaths;
    public int recovered;
    public int active;
    public int critical;
    public String casesPerOneMillion;
    public String deathsPerOneMillion;
    public int tests;
    public String testsPerOneMillion;

    public class CountryInfoModel {
        public int _id;
        public String iso2;
        public String ios3;
        public int lat;
        public int lng;
        public String flag;
    }

    public CountryModel() {

        countryInfo = new CountryInfoModel();
    }
}
