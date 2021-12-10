<template>
    <div v-if="loaded" class="information">
        <h1>Información Centro Deprtivo</h1>
        <h2>Nombre: <span>{{username}}</span></h2>
        <h2>Tipo: <span>{{tipo}}</span></h2>
        <h2>Correo electrónico: <span>{{email}}</span></h2> 
    </div>
</template>
<script>
import jwt_decode from "jwt-decode";
import axios from 'axios';
export default {
    
    data: function(){
        return {
            username: "",
            email: "",
            tipo: 0,
            loaded: true,
        }
    },
    methods: {
        getData: async function () {
            
            if (localStorage.getItem("token_access") === null || localStorage.getItem("token_refresh") === null) {
                this.$emit('logOut');
                return;
            }
            await this.verifyToken();

            let token = localStorage.getItem("token_access");
            let userId = jwt_decode(token).user_id.toString();

            

            axios.get(`https://sportclub-be.herokuapp.com/user/${userId}/`, 
            {headers: {'Authorization': `Bearer ${token}`}})


                .then((result) => {            
                    this.username = result.data.username;
                    this.email = result.data.email;
                    this.tipo = result.data.tipo;
                    alert("tipo en account: "+ this.username);
                })
                .catch(() => {
                    this.$emit('logOut');
                });
        },

        verifyToken: function () {

            return axios.post("https://sportclub-be.herokuapp.com/refresh/", 
            {refresh: localStorage.getItem("token_refresh")}, {headers: {}})
        
            .then((result) => {
                localStorage.setItem("token_access", result.data.access);
            })
            .catch(() => {
                this.$emit('logOut');
            });
        }
    },
    created: async function(){
        this.getData();
    },
}
</script>
<style>
    .information{
        margin: 0;
        padding: 0%;
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }


    .information h1{
        font-size: 60px;
        color: #0f1316;
    }
    .information h2{
        font-size: 40px;
        color: #283747;
    }
    .information span{
        color: crimson;
        font-weight: bold;
    }
</style>