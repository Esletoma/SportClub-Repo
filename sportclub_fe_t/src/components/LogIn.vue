<template>
  <div class="logIn_user">
    <div class="container_logIn_user">
      <h2>Iniciar sesión</h2>
      <form v-on:submit.prevent="processLogInUser" >
        <input type="text" v-model="user.username" placeholder="Username">
        <br>
        <input type="password" v-model="user.password" placeholder="Password">
        <br>
        <button type="submit">Iniciar Sesion</button>
      </form>
    </div>
  </div>
</template>

<script>
import jwt_decode from "jwt-decode";
import axios from 'axios';

export default {
  name: "LogIn",
  tipo: 0,

  data: function(){
    return {
      user: {
        username:"",
        password:"",
      }
    }
  },

  methods: {
    processLogInUser: function(){
      
      //alert("entró a process ")
      axios.post(
        "http://127.0.0.1:8000/login/",
        this.user,
        {headers: {}}
        )
        
        .then((result) => {
          //alert("crear datos");
          let dataLogIn = {
            
            username: this.user.username,
            token_access: result.data.access,
            token_refresh: result.data.refresh,
            tipo: this.tipo,
          }
          //this.$emit('completedLogIn', dataLogIn);
          this.cargarUsuario(dataLogIn);
        })
        .catch((error) => {
          if (error.response.status == "401")
            alert("ERROR 401: Credenciales Incorrectas.");
          else
            alert("ERROR: "+ error);
        });
    },
    cargarUsuario: function(dataL){
      alert("entró a cargar usuario");
            let token = dataL.token_access;
            //alert("token: "+ token);
            let userId = jwt_decode(token).user_id.toString();
            //alert("UserID: "+ userId);

            axios.get(`http://127.0.0.1:8000/user/${userId}/`, 
            {headers: {'Authorization': `Bearer ${token}`}})

                .then((result) => {            
                    alert("tipo: "+ result.data.tipo);
                    dataL.tipo = result.data.tipo;
                    this.$emit('completedLogIn', dataL);
                })
                .catch(() => {
                  alert("error en cargar usuario: ");
                    this.$emit('logOut');
                });

                return this.tipo;
    },
  }
}
</script>

<style>

  .logIn_user{
    margin: 0;
    padding: 0%;
    height: 100%;
    width: 100%;

    display: flex;
    justify-content: center;
    align-items: center;
  }

  .container_logIn_user {
    border: 3px solid #283747;
    border-radius: 10px;
    width: 25%;
    height: 60%;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .logIn_user h2{
    color: #283747;
  }

  .logIn_user form{
    width: 70%;
  }

  .logIn_user input{
    height: 40px;
    width: 100%;

    box-sizing: border-box;
    padding: 10px 20px;
    margin: 5px 0;

    border: 1px solid #283747;
  }

  .logIn_user button{
    width: 100%;
    height: 40px;
    
    color: #E5E7E9;
    background: #283747;
    border: 1px solid #E5E7E9;
    
    border-radius: 5px;
    padding: 10px 25px;
    margin: 5px 0;
  }

  .logIn_user button:hover{
    color: #E5E7E9;
    background: crimson;
    border: 1px solid #283747;
  }
</style>