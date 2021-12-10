<template>
  <div class="signUp_user">
    <div class="container_signUp_user">
      <div v-if="loaded" class="information">
        <form v-on:submit.prevent="updateAct" >
        <li>{{ actNom }} - {{oldHor}}</li>
        <br>

        <select ref="seleccionado">
            <option v-for="h in horarios" v-bind:value="h.id_horario" >{{ h.dia }} - {{ h.hora }}</option>
        </select>
       
        <br>
        
        <button type="submit">Actualizar</button>
      </form>
    </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import jwt_decode from "jwt-decode";

export default {
  
  data: function(){
    return {
      actNom : "",
      oldHor : "",
      body :{
        id_user : 0,
        centro : 0,
        actividad : 1,
        horario : 1
      },
      horarios: [],
      loaded: true,
    }
  },
  methods: {
    updateAct: function(){
        alert("entró a updateAct");
        let token = localStorage.getItem("token_access");
        let userId = jwt_decode(token).user_id.toString();

        this.body.id_user = userId;
        //this.body.centro = parseInt(userId);
        this.body.horario = this.$refs.seleccionado.value;
        //alert("body a enviar: "+ this.body);
        console.log(this.body);

        let idClass = localStorage.getItem('idActUpdate');

        axios.put(`http://127.0.0.1:8000/fusionupdate/${idClass}/`,
          this.body,
          {
            headers: {'Authorization': `Bearer ${token}`}
          }
        )
        .then((result) => {
            alert("Se realizó el registro");
        })
        .catch((error) => {
          console.log(error)
          alert("ERROR: Fallo en el registro. "+ error );
        });
    },
    getHorario : function (){

        axios.get("http://127.0.0.1:8000/horarioreadall/",{headers:{}})

        .then((result)=>{
            this.horarios = result.data
            console.log(this.horarios);
        })
        .catch(() => {
                alert("Error en getHorarios");
        });
    },
    cargarNombreActividad : async function (){
        let idClass = localStorage.getItem('idActUpdate');

        axios.get(`http://127.0.0.1:8000/fusionread/${idClass}/`)

        .then((result)=>{
            alert("funcionó fusion read");
            console.log(result.data);
            this.actNom = result.data.actividad.nombre;
            this.oldHor = result.data.horario.dia + "-" + result.data.horario.hora;
            this.body.centro = result.data.id_centro;
            this.body.actividad = result.data.actividad.id_actividad;
        })
        .catch((e) => {
            alert("Error en Fusión Read:" + e);
        });

    },
    verifyToken: function () {

        //alert("entró a verify token");

            return axios.post("https://sportclub-be.herokuapp.com/refresh/", 
            {refresh: localStorage.getItem("token_refresh")}, {headers: {}})
        
            .then((result) => {
                localStorage.setItem("token_access", result.data.access);
                //alert("hizo el refresh");
            })
            .catch(() => {
                this.$emit('logOut');
            });
    },
  },
  created: async function(){
        
        this.getHorario();
        this.cargarNombreActividad();
  },
}
</script>

<style>

  .signUp_user{
    margin: 0;
    padding: 0%;
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .container_signUp_user {
      border: 3px solid #283747;
    border-radius: 10px;
    width: 25%;
    height: 60%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .signUp_user h2{
    color: #283747;
  }

  .signUp_user form{
    width: 70%;
  }

  .signUp_user input{
    height: 40px;
    width: 100%;
    box-sizing: border-box;
    padding: 10px 20px;
    margin: 5px 0;
    border: 1px solid #283747;
  }

  .signUp_user button{
    width: 100%;
    height: 40px;
    color: #E5E7E9;
    background: #283747;
    border: 1px solid #E5E7E9;
    border-radius: 5px;
    padding: 10px 25px;
    margin: 5px 0 25px 0;
  }

  .signUp_user button:hover{
    color: #E5E7E9;
    background: crimson;
    border: 1px solid #283747;
  }
</style>