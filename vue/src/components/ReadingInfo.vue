<template>
    <div class="familymembers" v-on:click="onClick()">
        {{curUser.username}} 
        <div id="flexcontainer" v-if="isVisible">
        <!--<li>Total Minutes Read: {{ReadingActivity.minutes_read}}</li>
        <li>Books Completed: {{ReadingActivity.books_read}}</li>
        <li>Books Reading: {{ReadingActivity.books_reading}}</li>-->
        <p>Total Minutes Read: {{ReadingActivity.minutes_read}}</p>
        <p>Books Completed: {{ReadingActivity.books_read}}</p>
        <p>Books Reading: {{ReadingActivity.books_reading}}</p>
        </div>
    </div>
</template>

<script>
import FamilyService from "../services/FamilyService";

export default {
  name: "reading-info",
  props: {
      curUser: Object
  },
  data() {
        return {
            isVisible: false,
            ReadingActivity: {
                minutes_read: "",
                books_read: "",
                books_reading: ""
            }
        }
    },
   methods: {
       onClick() {
           FamilyService.getReadingActivity(this.curUser.username).then((response) => {
               this.ReadingActivity = response.data;
               if(!this.isVisible){
                   this.isVisible = true;
               } else {
                   this.isVisible = false;
               }
           })
       }
   }
}
</script>

<style scoped>

#flexcontainer {
    display: flex;
    justify-content: space-evenly;
    margin-top: 5px;
}

.familymembers {
    width: 50%;
    background: #fffdfd85;
    position:relative;
    margin: 0 auto;
    margin-bottom: 5px;
    color:rgb(54, 52, 52);
    font-size: 23px;
    font-family: "Mouse Memoirs", sans-serif;
    border-radius: 15px;
    border: solid;
    border-color: #22162B;
}
.familymembers:hover {
    background-color: #b3adad85;
}

</style>