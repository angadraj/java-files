import React, {useState, useEffect} from 'react';
import Header from './components/Header/Header';
import List from './components/List/List';
import Map from './components/Map/Map';
import {CssBaseline, Grid} from '@material-ui/core'
import {getPlacesData} from './api';

const App = () => {
    const [places, setPlaces] = useState([]);
    const [cords, setCords] = useState({});
    const [bounds, setBounds] = useState(null);

    const [type, setType] = useState('restaurants');
    const [rating, setRating] = useState('');

    const [childClicked, setChildClicked] = useState(null);
    const [isLoading, setLoading] = useState(false);

    const [filteredPlaces, setFilteredPlaces] = useState([]);

    useEffect(() => {
        navigator.geolocation.getCurrentPosition(({coords: {latitude, longitude}}) => {
            setCords({lat: latitude, lng: longitude});
        });
    }, [])
    
    useEffect(() => {
        setLoading(true);
        if (bounds && bounds.sw && bounds.ne) {
            getPlacesData(type, bounds.sw, bounds.ne)
            .then((data) => {
                setPlaces(data ?. filter((place) => place.name && place.num_reviews > 0));
                setFilteredPlaces([]);
                setLoading(false);
                setRating('');
            });
        }

    }, [type, bounds])

    useEffect(() => {
        const temp = places.filter((place) => {
            return (place.rating > rating);
        })
        setFilteredPlaces(temp);
    }, [rating])

    return (
        <>
            <CssBaseline/>
            <Header setCords={setCords} />
            <Grid container spacing={3} style={{width: "100%"}}>
                <Grid item xs={12} md={4}>
                    <List
                        places = {filteredPlaces.length ? filteredPlaces : places}
                        childClicked = {childClicked}
                        isLoading = {isLoading}
                        type = {type}
                        setType = {setType}
                        rating = {rating}
                        setRating = {setRating}
                    />
                </Grid>
                <Grid item xs={12} md={8}>
                    <Map
                        setCords = {setCords}
                        setBounds = {setBounds}
                        cords = {cords}
                        places = {filteredPlaces.length ? filteredPlaces : places}
                        setChildClicked = {setChildClicked}
                    />
                </Grid>
            </Grid>
        </>
    );
}

export default App;
