<template>
    <div id="navbox">
        <p><router-link class="linktext" v-bind:to="{name: 'profile'}">Profile</router-link> </p>
        <p><router-link class="linktext" :to="{ name: 'family'}">Family</router-link></p>
        <p><router-link class="linktext" :to="{ name: 'readingList' }">Books</router-link></p>
        <p><router-link class="linktext" :to="{ name: 'prizes' }">Prizes</router-link></p>
        <p><router-link class="linktext" :to="{ name: 'settings' }">Settings</router-link></p>
    </div>
</template>

<script>


export default {
    name: 'navbar',
    props: {
    },
    data() {
        return {

        }
    },
    methods: {
        
    },
    computed: {
        
    }
}
</script>

<style scoped>
    #navbox {
        margin-top: 0px;
        position: absolute;
        top: 150px;
        left: 5%;
        color: #F8C630;
        font-family: "Mouse Memoirs", sans-serif;
        font-size: 25px;
    }
    .linktext:hover{
        color: orangered;
    }
    .linktext {
        color: #F8C630;
    }
    /*.profilelink,
    .readinglistlink,
    .familylink,
    .prizeslink {
      color: #F8C630;

    }*/
</style>