<template>
  <div id="register" class="text-center">
    <div class="imagediv">
        <img class="background" src="pexels-janko-ferlic-590493.jpg">
    <form class="form-register" @submit.prevent="register">
      <h2>Create Account</h2>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <label for="username" class="sr-only">Username: &nbsp;</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <div id="registerpassword">
      <label for="password" class="sr-only">Password: &nbsp;</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      </div>
      <label for="confirmPassword" class="sr-only">Confirm Password: &nbsp;</label>
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <br/>
      <label for="parentOrChild" class="sr-only">Are you a parent?: &nbsp;</label>
      <input
        type="checkbox"
        id="parentOrChild"
        class="form-control"
        v-model="isParent"
      />
      <div id="createaccountbtn">
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
      </div>
      <div id="haveanaccountlink">
      <router-link id="links" :to="{ name: 'login' }">Have an account?</router-link>
      </div>
    </form>
    <h1>Bookworm</h1>
    <img id="logo" src="mini logo.png">

    </div>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      isParent: false,
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        
        // set role
        if(this.isParent){
          this.user.role = 'ROLE_ADMIN';
        } else {
          this.user.role = 'ROLE_USER'
        }
        
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
.background{
  width: 100vw;
  height: 100vh;
}

.form-register{
  position:absolute;
  top: 15rem;
  left: 5rem;
  color: white;
  border: solid;
  border-color: white;
  border-width: 1px;
  border-radius: 3px;
  padding-right: 112px;
  padding-left: 10px;
  padding-top: 6px;
  padding-bottom: 10px;
  text-decoration: none;
  background-color: rgba(69, 31, 85, 0.5);
}

#links{
  text-decoration: none;
  color:white;
}

h1 {
  position:absolute;
  font-size: 3em;
  top: 9.5rem;
  left: 10rem;
  color:white;
  font-family: "Pangolin", sans-serif;
}

h2 {
  margin-top:10px;
  margin-bottom: 10px;
  font-size: 3em;
  font-family: "Pangolin", sans-serif;
}

#password {
  margin-left: 70px;
}

#username {
  margin-left: 66px;
}

#createaccountbtn {
  margin-left: 154px;
}

#links {
  margin-left: 154px;
}

.sr-only {
  font-size: 1.2em;
}


#logo {
  position: absolute;
  top: 11rem;
  left: 4rem;
  height: 7.2rem;
}

</style>
