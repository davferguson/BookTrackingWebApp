<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>
    <router-view />
  </div>
</template>

<script>
export default {
  name: "app",
  components: {

  },
  data() {
    return {
      
    }
  },
  created() {

  }
};
</script>

<style>
#nav{
  left:10%;
  padding-left:10%;
}

body {
  margin: 0;
}

@font-face {
  font-family: "Mouse Memoirs";
  src: local("Mouse Memoirs"),
   url(./fonts/MouseMemoirs-Regular.ttf) format("truetype");
}

@font-face {
  font-family: "Pangolin";
  src: local("Pangolin"),
  url(./fonts/Pangolin-Regular.ttf) format("truetype");
}

.homebackground {
  width: 100vw;
  height: 100vh;
  min-height: 50rem;
} 
.home {
  position:absolute;
  top:20px;
  left: 10%;
  color: #724E91;
  text-decoration: none;
  font-family: "Mouse Memoirs", sans-serif;
  font-size: 150%;
}
.mainwindow {
  text-align: center;
  position: absolute;
  top: 50px;
  left: 18%;
  background: rgba(114, 78, 145, 0.7);
  height: 50rem;
  width: 70vw;
}
.error-message {
  color: red;
}

</style>