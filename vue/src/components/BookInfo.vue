<template>
    <div class="container">
        <div class="img">
            <img v-bind:src="'http://covers.openlibrary.org/b/isbn/' + book.isbn + '-M.jpg'"/>
        </div>
        <div class="info">
            {{ book.book_name }}<br>
            Author {{ book.author }}<br>
            ISBN {{ book.isbn }}<br>
            minutes read: {{ this.minutes_read }}<br>
            finished?:
        </div>
    </div>
</template>

<script>
import BookService from '../services/BookService';

export default {
    name: 'book-info',
    props: {
        book: Object,
    },
    data() {
        return {
            url: "/book/" + this.book.isbn,
            user: this.$store.state.user,
            minutes_read: 0,
            readingActivity: {
              username: this.$store.state.user.username, 
              minutes_read: "",
              isbn: this.book.isbn
            }

        }
    },
    methods: {    
    },
    created() {
        BookService.minutesRead(this.readingActivity.username, this.readingActivity.isbn).then(response => {
                this.minutes_read = response.data;
            });
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
  font-size: 30px;
}
img {
    /* height: 14rem; */
    width: 10rem;
}
.img {
    grid-area: image;
}
.info {
    grid-area: info;
}
</style>