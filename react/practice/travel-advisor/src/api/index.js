import axios from 'axios';

export const getPlacesData = async (type, sw, ne) => {
    try {
        // request
        const {data: {data}} = await axios.get(`https://travel-advisor.p.rapidapi.com/${type}/list-in-boundary`, {
            method: 'GET',
            params: {
                tr_longitude: ne.lng,
                tr_latitude: ne.lat,
                bl_longitude: sw.lng,
                bl_latitude: sw.lat,
            },
            headers: {
                'x-rapidapi-host': 'travel-advisor.p.rapidapi.com',
                'x-rapidapi-key': '6b09e12069msh1b787fe9e5bfde7p198115jsnfe4a150ca559'
            }
        });
        return data;
    } catch (e) {
        console.log(e);
    }
}