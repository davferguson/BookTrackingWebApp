import axios from 'axios';


export default {
    selectReadingWithinDateTime(startDate, endDate, username) {
        return axios.get('/reading_within_date_time', { params: {startDate, endDate, username}});
    },
}