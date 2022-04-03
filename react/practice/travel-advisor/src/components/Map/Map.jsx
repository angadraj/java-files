import React, {useState, useEffect} from 'react';
import GoogleMapReact from 'google-map-react';
import {Paper, Typography, useMediaQuery} from '@material-ui/core';
import LocationOnOutlinedIcon from '@material-ui/icons/LocalActivityOutlined';
import Rating from '@material-ui/lab/Rating';

import useStyles from './styles';

const Map = ({setCords, setBounds, cords, places, setChildClicked}) => {
    const classes = useStyles();
    const isMobile = useMediaQuery('(min-width: 600px)');

    return (
        <div className={classes.mapContainer}>
            <GoogleMapReact
                bootstrapURLKeys = {{key: 'AIzaSyBLqUM3Dw-Qd6rXxZdnFbKwgdFQbm-CG9k'}}
                defaultCenter = {cords}
                center = {cords}
                defaultZoom = {14}
                margin = {[50, 50, 50, 50]}
                options = {''}
                onChange = {(e) => {
                    setCords({lat: e.center.lat, lng: e.center.lng});
                    setBounds({sw: e.marginBounds.sw, ne: e.marginBounds.ne});
                }}
                onChildClick={(child) => {
                    setChildClicked(child);
                }}
            >
                {
                    places?.map((place, i) => (
                        <div 
                            className={classes.markerContainer}
                            lat={Number(place.latitude)} 
                            lng={Number(place.longitude)}
                            key={i}    
                        >
                            {
                                <Paper elevation={3} className={classes.paper}>
                                    <Typography className={classes.typography} variant="subtitle2" gutterBottom>
                                        {place.name}
                                    </Typography>
                                    <img 
                                        className={classes.pointer}
                                        src={place.photo ? place.photo.images.large.url : ''}
                                    />
                                    <Rating size="small" value={Number(place.rating)} readOnly />
                                </Paper>
                            }
                        </div>
                    ))
                }
            </GoogleMapReact>
        </div>
    );
}

export default Map;