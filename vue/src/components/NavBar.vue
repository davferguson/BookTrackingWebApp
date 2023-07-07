<template>
    <div id="navbox">
        <p><router-link class="profilelink" v-bind:to="{name: 'profile'}">Profile</router-link> </p>
        <p><router-link class="familylink" :to="{ name: 'family'}">Family</router-link></p>
        <p><router-link class="readinglistlink" :to="{ name: 'readingList' }">Reading List</router-link></p>
        <p><router-link class="prizeslink" :to="{ name: 'prizes' }">Prizes</router-link></p>
    </div>
</template>

<script>


export default {
    name: 'navbar',
    props: {
    },
    data() {
        return {
            url: "/book/" + this.book.isbn

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
        left: 10%;
        color: #F8C630;
        font-family: "Mouse Memoirs", sans-serif;
        font-size: 25px;
    }
</style>