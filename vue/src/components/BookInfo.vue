<template>
    <div class="container">
        <div class="img">img</div>
        <div class="info">info</div>
    </div>
</template>

<script>

export default {
    name: 'book-info',
    props: {
        book: Object,
    },
    data() {
        return {
            url: "/book/" + this.book.isbn,
            user: this.$store.state.user,
            readingActivity: {
              username: this.$store.state.user.username, 
              minutes_read: "",
              isbn: this.book.isbn
            }

        }
    },
    methods: {    
    },
    computed: {
    }
}
</script>

<style scoped>
.container {
  display: grid;
  grid-template-areas: 
  'image info info info info';
  gap:10px;
  background-color: #2196F3;
  padding: 0px;
}
.container > div {
  background-color: rgba(255, 255, 255, 0.8);
  text-align: center;
  padding: 20px 0;
  font-size: 30px;
}
.img {
    grid-area: image;
}
.info {
    grid-area: info;
}
</style>