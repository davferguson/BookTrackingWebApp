<template>
<div>
    <img class="homebackground" src="living-room.jpg">
        <NavBar/>
         <div class="mainwindow">
            <div id="prize-header">
             <h1>Prizes!</h1>
             <img id="trophy" src="trophy1.png">
             <img id="trophy" src="trophy1.png">
             <img id="trophy" src="trophy1.png">
            </div>
             
             <div v-if="isAdmin" id="createPrize">
                <button class="create-prize-btn" v-on:click.prevent="toggleCreatePrize()">Create a Prize</button>
            </div>
            <div id="no-family-error" v-if="!isPartOfFamily && isCreatePrize">
                Must be part of a family to create a prize!
            </div>
             
             <div v-if="isAdmin" id="createPrize">
                <!-- <h3>Create a prize: </h3> -->
                <br>
                <form v-if="isCreatePrize && isPartOfFamily" @submit.prevent="submitPrize">
                    <input type="text" placeholder="Prize name" id="pname" name="pname" v-model="prize.name" required>
                    <br>
                    <textarea v-on:keyup="prizeCharacterCounter()" id="pdescription" placeholder="Write prize description..." name="pdescription" rows="4" cols="50" maxlength="499" v-model="prize.description" required></textarea>
                    <div id="character-counter">
                        <span id="typed-characters">0</span>
                        <span>/</span>
                        <span id="maximum-characters">499</span>
                    </div>
                    <fieldset>
                        <legend id="goal-type-legend">Select goal type:</legend>
                        <input type="radio" class="goal-radio" name="goal_type" value="minutes_read" v-model="prize.goal_type" required>
                        <label for="minutes-read-radio">Minutes Read</label>
                        <input type="radio" class="goal-radio" name="goal_type" value="books_finished" v-model="prize.goal_type">
                        <label for="books-finished-radio">Books Finished</label>
                        <input type="number" placeholder="Goal value" v-model="prize.goal_val" required>
                    </fieldset>
                    <div>
                        <label for="pstart">Start Date: </label>
                        <input type="datetime-local" id="pstart" v-model="prize.start_date" required>
                    </div>
                    <br>
                    <label for="pend">End Date: </label>
                    <input type="datetime-local" id="pend" v-model="prize.end_date" required>
                    <br>
                    <input type="submit" id="prize-submit" value="Submit Prize">
                    <div id="no-family-error" v-if="!isPartOfFamily">
                        Must be part of a family to create a prize!
                    </div>
                </form>
             </div>
            <h2 v-if="!isCreatePrize">Current Prizes</h2>
            <PrizeList v-if="!isCreatePrize" :prizes="this.$store.state.prizes"/>
             
        </div>
</div>
</template>   

<script>   
import PrizeService from '../services/PrizeService.js';
import FamilyService from '../services/FamilyService.js';
import NavBar from '@/components/NavBar.vue';
import PrizeList from '@/components/PrizeList.vue';

    export default {  
        components: {
            NavBar,
            PrizeList
         }, 
        data() {
            return {
            username: this.$store.state.user.username,
            isAdmin: false,
            isCreatePrize: false,
            isPartOfFamily: true,
            familyId: 0,
            prize: {
                name: "",
                description: "",
                goal_type: "",
                goal_val: "",
                start_date: "",
                end_date: ""
            }
            }
        },
        methods: {
            toggleCreatePrize() {
                if(this.isCreatePrize){
                    this.isCreatePrize = false;
                } else {
                    this.isCreatePrize = true;
                }
            },
            submitPrize() {
                if(this.isPartOfFamily){
                    PrizeService.createPrize(this.prize, this.familyId).then((response) => {
                    if (response.status === 201) {
                        this.$router.push('/actioncompleted')
                    }
                });
                }
                
            },
            prizeCharacterCounter() {
                const prizeTextAreaElement = document.querySelector("#pdescription");
                const typedCharacters = prizeTextAreaElement.value.length;
                const typedCharactersElement = document.querySelector("#typed-characters");
                typedCharactersElement.textContent = typedCharacters;
            }
        },
        created() {
            // PrizeService.list().then( (response) => {
            //    this.$store.state.prizes = response.data; 
            // });
            if(this.$store.state.user.authorities[0].name == 'ROLE_ADMIN'){
                this.isAdmin = true;
            }
            FamilyService.getFamilyIdFromUsername(this.username).then((response) => {
                this.familyId = response.data;
                
            }).catch((error) => {
                if(error.response.status === 404){
                    this.isPartOfFamily = false;
                    return;
                }
            }).finally(() => {
                PrizeService.listAvailablePrizes(this.familyId).then((response) => {
                    this.$store.state.prizes = response.data;
                })
            });
        }
    };

</script>

<style scoped>
#prize-header {
    display:flex;
    background-color: #3a2649;
    justify-content: center;
    align-items: center;
}
#goal-type-legend {
color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
  font-size: 1.6rem;
}
.goal-radio {
    width: 10px;
    margin-right: 0;
}
#minutes-read-radio {
    width: 10px;
    margin-right: 0;
}
#no-family-error {
    text-align: center;
    color: red;
}
#prize-submit {
  background-color: #22162B;
  color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
  font-size: 1.2rem;
  margin-top: 1rem;
  margin-left: 25%;
  width: 50%;
}
#prize-submit:hover {
    background-color: #4a305e;
}
.create-prize-btn {
  background-color: #22162B;
  color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
  font-size: 1.8rem;
}
.create-prize-btn:hover {
  background-color: #4a305e;
}
label {
    color: #f8c630;
    font-family: "Mouse Memoirs", sans-serif;
    font-size: 1.2rem;
}
form {
    background-color: #503666b3;
    width: 95%;
    max-width: 30rem;
    display: inline-block;
    margin-bottom: 1rem;
    padding-top: 1rem;
    padding-bottom: 1rem;
}
input {
    width: 90%; 
    margin-left: 1rem;
    margin-right: 1rem;
}
label {
    margin-left: 1rem;
}
#character-counter {
    text-align: right;
    color: #f8c630;
    margin-right: 5%;
}
#pname {
    border-width: 1px;
}
#pdescription {
    resize: none;
    width: 90%;
    margin-left: 1rem;
    margin-right: 1rem;
}
#createPrize {
    display:flex;
    justify-content: center;
}
h1 {
  font-size: 40px;
  color: #f8c630;
  background-color: #3a2649;
  padding-top: 7px;
  padding-bottom: 7px;
  margin: 0;
  font-family: "Mouse Memoirs", sans-serif;
  text-align: center;
}
h2 {
  font-size: 30px;
  color: #f8c630;
  background-color: #3a2649;
  padding-top: 7px;
  padding-bottom: 7px;
  margin: 0;
  font-family: "Mouse Memoirs", sans-serif;
  text-align: center;
}
#trophy {
    height: 3rem;
}

</style>