<template>
    <div>
    
        <div class="book-container">
            <BookInfo v-bind:book="book" v-for="book in books" v-bind:key="book.isbn" />
            <!-- <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/>
            <BookInfo/> -->
        </div>
    </div>
      
    </template>
    
    <script>
    import BookInfo from '@/components/BookInfo.vue';
    
    export default {
        name: 'book-list',
        props: {
            books: Array,
        },
        components: {
            BookInfo
        }
    }
    </script>
    
    <style scoped>
    
    .book-container {
        background-color: #22162B;
        display: grid;
        gap: 1%;
        grid-auto-flow: row;
        margin-bottom: 0;
        padding-bottom: 0;
        height: 30rem;
    
    
        overflow-x: auto;
        overscroll-behavior-inline: contain;
        /* border: 10px solid #57386e;
        border-radius: 1px; */
        /* height: 100%; */
    
    }
    </style>