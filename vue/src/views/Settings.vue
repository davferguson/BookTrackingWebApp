<template>
    <div>
      <img class="homebackground" src="living-room.jpg">
        <NavBar/>
        <div class="mainwindow">
            <h3>Settings</h3>
            <button v-on:click.prevent="updateIsDeleteAccount(true)">Delete Account</button>
            <div id="delete_message" v-if="isDeleteAccount">
                Are you sure you want to delete your account? All user data will be lost forever.
                <div id="delete_choice">
                    <button v-on:click.prevent="deleteAccount()">Yes</button>
                    <button v-on:click.prevent="updateIsDeleteAccount(false)">No</button>
                </div>
            </div>
            <br/>
            <button v-on:click.prevent="updateIsChangeUsername()">Change Username</button>
            <br/>
            <input v-if="isChangeUsername" name="newusername" type="text" placeholder="New Username" v-model="changeUsernameObj.newUsername"/>
            <input v-if="isChangeUsername" name="password" type="password" placeholder="password" v-model="changeUsernameObj.password"/>
            <button v-if="isChangeUsername" v-on:click.prevent="changeUsername()">Submit</button>
            <div v-if="!isValid">{{ errorMessage }}</div>
        </div>
    
    </div>
  </template>

<script>
import NavBar from '@/components/NavBar.vue';
import UserService from '../services/UserService';

export default {
  name: "settings",
  components: {
    NavBar
  },
  data() {
    return {
      username: this.$store.state.user.username,
      isDeleteAccount: false,
      isChangeUsername: false,
      newUsername: '',
      changeUsernameObj: {
        curUsername: '',
        newUsername: '',
        password: ''
      },
      errorMessage: '',
      isValid: true
    }
  },
  methods: {
    changeUsername() {
      this.changeUsernameObj.curUsername = this.$store.state.user.username;
      UserService.changeUsername(this.changeUsernameObj).then((response) => {
        console.log(response.status);
        if(response.status === 200){
          this.isValid = true;
          this.$router.push('/login');
          // this.$store.commit('SET_USERNAME', this.changeUsernameObj.newUsername);
        }
      }).catch(error => {
        const response = error.response;
        if (response.status === 400) {
          this.errorMessage = response.data.message;
          this.isValid = false;
          console.log(response.data.message);
        }
      });
      
    },
    updateIsChangeUsername() {
      this.isChangeUsername ? this.isChangeUsername = false : this.isChangeUsername = true;
      this.isValid = true;
    },
    updateIsDeleteAccount(deleting) {
        this.isDeleteAccount = deleting;
    },
    deleteAccount() {
        UserService.deleteUser(this.user);
        this.$router.push('/login');
    }
  },
  created() {

  }
};
</script>

<style scoped>
button {
  background-color: #22162B;
  color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
  font-size: 1.8rem;
}
button:hover {
    background-color: #4a305e;
}
h1 {
  margin: 10px;
  margin-left: 0px;
}
h3 {
  font-size: 40px;
  color: #f8c630;
  background-color: #3a2649;
  padding-top: 7px;
  padding-bottom: 7px;
  margin: 0;
  font-family: "Mouse Memoirs", sans-serif;
}
input {
  font-size: 1rem;
}
#delete_message {
  color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
  font-size: 20px;
  background-color: #3a2649;
  padding-top: 7px;
  padding-bottom: 7px;
}
  
</style>