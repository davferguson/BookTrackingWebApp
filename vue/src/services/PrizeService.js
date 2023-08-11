import axios from 'axios';


export default {
    list() {
        return axios.get('/prizes')
    },
    listAvailablePrizes(familyId) {
        return axios.get('/available_prizes', { params: {familyId}})
    },
    createPrize(prize, family_id) {
        return axios.post('/create_prize', prize, { params: {family_id}});
    }
}