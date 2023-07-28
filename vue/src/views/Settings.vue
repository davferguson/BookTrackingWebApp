<template>
    <div>
      <img class="homebackground" src="living-room.jpg">
        <NavBar/>
        <div class="settingswindow">
            <h3>Settings</h3>
            <button v-on:click.prevent="updateIsDeleteAccount(true)">Delete Account</button>
            <div id="delete_message" v-if="isDeleteAccount">
                Are you sure you want to delete your account? All user data will be lost forever.
                <div id="delete_choice">
                    <button v-on:click.prevent="deleteAccount()">Yes</button>
                    <button v-on:click.prevent="updateIsDeleteAccount(false)">No</button>
                </div>
            </div>
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
    }
  },
  methods: {
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
.settingswindow {
  text-align: center;
  position: absolute;
  top: 50px;
  left: 18%;
  background: rgba(114, 78, 145, 0.7);
  height: 45rem;
  width: 70vw;
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
#delete_message {
  color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
  font-size: 20px;
  background-color: #3a2649;
  padding-top: 7px;
  padding-bottom: 7px;
}
  
</style>