import { createContext, PropsWithChildren, useContext } from 'react';
import { CachePolicies, useFetch } from 'use-http';
import { cityUrls } from '../api/location/city';
import { countryUrls } from '../api/location/country';
import Spinner from '../components/common/spinner/Spinner';
import {
  ICountriesReduced,
  reduceCountries,
} from '../pages/auth/components/phone-input/PhoneInput';
import { ICity } from '../types/interfaces/location/ICity';
import { ICountry } from '../types/interfaces/location/ICountry';

type LocationContextType = {
  countries: ICountry[];
  countriesReduced: ICountriesReduced;
  cities: ICity[];
};

const LocationContext = createContext<LocationContextType | null>(null);

export function LocationProvider({ children }: PropsWithChildren) {
  const { data: citiesFetch, loading: loadingCities } = useFetch<ICity[]>(
    cityUrls.fetchAll,
    {
      cachePolicy: CachePolicies.CACHE_AND_NETWORK,
    },
    []
  );

  const { data: countriesFetch, loading: loadingCountries } = useFetch<
    ICountry[]
  >(
    countryUrls.fetchAll,
    {
      cachePolicy: CachePolicies.CACHE_AND_NETWORK,
    },
    []
  );

  if (loadingCities || loadingCountries) {
    return <Spinner />;
  }

  const cities = [...(citiesFetch || [])];
  const countries = [...(countriesFetch || [])];
  const countriesReduced = reduceCountries(countries);

  return (
    <LocationContext.Provider
      value={{
        cities,
        countries,
        countriesReduced,
      }}>
      {children}
    </LocationContext.Provider>
  );
}

export const useLocationContext = () => {
  const locationContext = useContext(LocationContext);

  if (!locationContext) {
    throw new Error(
      'useLocationContext has to be used within <LocationContext.Provider>'
    );
  }

  return locationContext;
};
