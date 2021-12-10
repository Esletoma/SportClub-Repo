<template>
  <div class="signUp_user">
    <div class="container_signUp_user">
      <h2>Agregar Actividad</h2>
      <form v-on:submit.prevent="addAct" >
        <input type="text" v-model="act.nombre" placeholder="Nombre">
        <br>

        <input type="number" v-model="act.capacidad" placeholder="Capacidad">
        <br>

        <select ref="seleccionado">
          <option v-for="h in horario" v-bind:value="h.id_horario" >{{ h.dia }} - {{ h.hora }}</option>
        </select>
       
        <br>
        
        <button type="submit">Guardar</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import jwt_decode from "jwt-decode";

export default {
  
  data: function(){
    return {

      act: {
        id_user:0,
        nombre: "",
        capacidad: 0,
      },
      horario: [],
      
    }
  },
  methods: {
    addAct: async function(){
        alert("entró a addAct");
        await this.verifyToken();

        let token = localStorage.getItem("token_access");
        let userId = jwt_decode(token).user_id.toString();

        this.act.id_user = userId;

        alert("id logueado: "+ this.act.id_user);
        //alert("token: "+ this.act.nombre);

        axios.post(
          "http://127.0.0.1:8000/actividad/", this.act,
          {
            headers: {'Authorization': `Bearer ${token}`}
          }
          //{id_user:userId,nombre:this.act.nombre,capacidad:this.act.capacidad},
          
        )
        .then((result) => {
            alert("Se realizó el registro. Id act: "+ result.data);
            this.addFusion(result.data,this.$refs.seleccionado.value);
        })
        .catch(() => {
          //console.log(error)
          alert("ERROR: Fallo en el registro." );
        });
    },
    getHorario : function (){
        alert("entró a get horario");
        axios.get("http://127.0.0.1:8000/horarioreadall/",{headers:{}})

        .then((result)=>{
            this.horario = result.data
            console.log(this.horario);
        })
        .catch(() => {
                alert("Error en getHorarios");
        });
    },
    verifyToken: function () {

        alert("entró a verify token");

            return axios.post("http://127.0.0.1:8000/refresh/", 
            {refresh: localStorage.getItem("token_refresh")}, {headers: {}})
        
            .then((result) => {
                localStorage.setItem("token_access", result.data.access);
                alert("hizo el refresh");
            })
            .catch(() => {
                this.$emit('logOut');
            });
    },
    addFusion: function(idact,idhor){
      alert("id act: "+ idact + "id hor: "+ idhor);
      let token = localStorage.getItem("token_access");
      let userId = jwt_decode(token).user_id.toString();

      let clase = {
          id_user :userId,
          centro :userId,
          actividad :idact,
          horario :idhor
          };

      axios.post(
          "http://127.0.0.1:8000/fusion/",
          clase,
          {
            headers: {'Authorization': `Bearer ${token}`}
          }
        )

        .then((result)=>{
          alert("Funcionó insertar Fusión");
        })
        .catch((e)=>{

        });
    },
  },
  created: function(){
        alert("entró a created");
        this.getHorario();
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