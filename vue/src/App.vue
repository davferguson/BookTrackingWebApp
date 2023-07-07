<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>
    <router-view />
  </div>
</template>


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
</style>