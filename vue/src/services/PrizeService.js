import axios from 'axios';


export default {
    list() {
        return axios.get('/prizes')
    },
    createPrize(prize, family_id) {
        return axios.post('/create_prize', prize, { params: {family_id}});
    }
}