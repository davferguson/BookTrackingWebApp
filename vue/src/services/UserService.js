import axios from 'axios';


export default {

    deleteUser(user) {
        return axios.post(`/delete_user`, user);
    },
    changeUsername(changeUsernameObj) {
        return axios.post('/change_username', changeUsernameObj);
    }
    
}