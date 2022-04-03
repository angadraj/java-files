import React from 'react';
import {Box, Typography, Button, Card, CardMedia, CardContent, CardActions, Chip} from '@material-ui/core';
import LocationOnIcon from '@material-ui/icons/LocationOn';
import PhoneIcon from '@material-ui/icons/Phone';
import Rating from '@material-ui/lab/Rating';
import useStyles from './styles';

const PlaceDetails = ({place, selected, refProp}) => {
    const classes = useStyles();

    if (selected) {
        refProp ?.current?.scrollIntoView({
            behavior: 'smooth'
        });
    }

    return (
        <Card elevation={6}>
            <CardMedia
                style={{height: 350}}
                image={place.photo ? place.photo.images.large.url : ''}
                title={place.name}
                component='img'
            />
            <CardContent>
                <Typography gutterBottom variant="h5">{place.name}</Typography>
                <Box style={{display: "flex", justifyContent: "space-between"}}>
                    <Typography variant="subtitle1">Distance</Typography>
                    <Typography gutterBottom variant="subtitle1">{Number(place.distance).toFixed(2)} Km</Typography>
                </Box>

                <Box style={{display: "flex", justifyContent: "space-between"}}>
                    <Typography variant="subtitle1">Ranking</Typography>
                    <Typography gutterBottom variant="subtitle1">{place.ranking}</Typography>
                </Box>
                {
                    place ?.awards ?.map((award) => (
                        <Box my={1} style={{display: "flex", justifyContent: "space-between"}}>
                            <img src={award.images.small} />
                            <Typography variant="subtitle2" color="textSecondary">{award.display_name}</Typography>
                        </Box>
                    ))
                }
                {
                    place?.cuisine?.map(({name}) => (
                        <Chip key={name} size="small" label={name} className={classes.chip} />
                    ))
                }
                {
                    place?.address && (
                        <Typography gutterBottom variant="body2" color="textSecondary" className={classes.subtitle2}>
                            <LocationOnIcon /> {place.address}
                        </Typography>
                    )
                }
                <CardActions>
                    <Button size="small" color="primary" onClick={() => window.open(place.web_url, '_blank')}>
                        Trip Advisor
                    </Button>
                    <Button size="small" color="primary" onClick={() => window.open(place.website, '_blank')}>
                        WebSite
                    </Button>
                </CardActions>

            </CardContent>
        </Card>
    );
}

export default PlaceDetails;