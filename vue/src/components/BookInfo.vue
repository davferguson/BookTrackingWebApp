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
  /* grid-template-areas: 
  'image info info info info'; */
  grid-template-columns: 10.3rem 1fr;
  gap:0px;
  background-color: rgba(114,78,145,0.7);
  padding: 0px;
}
.container > div {
  text-align: center;
  font-size: 1.3rem;
  border-top: 5px solid #3a2649;

}
img {
    max-width: 10rem;
}
.img {
    /* grid-area: image; */
    border-right: 5px solid #3a2649;
    /* background-color: #3a2649; */
    /* width: 10rem; */
}
.info {
    /* grid-area: info; */
    color: #f8c630;
    font-family: "Mouse Memoirs", sans-serif;
}
</style>