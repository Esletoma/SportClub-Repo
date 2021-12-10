<template>
    <div v-if="loaded" class="information">
        <ul>
           <li v-for="act in actividades">
               {{ act.actividad.nombre }}
               {{ act.actividad.capacidad }}
               {{ act.horario.dia }} - {{ act.horario.hora }}
                &nbsp;
               <button v-bind:key = act.id_clase v-on:click="eliminarAct(act.id_clase)">Eliminar</button>
                &nbsp;
               <button v-bind:key = act.id_clase v-on:click="actualizarAct(act.id_clase)">Cambiar Horario</button>
            </li> 
               
        </ul>
        <button v-on:click="crearAct">Agregar Actividad</button>
    </div>
</template>
<script>
import jwt_decode from "jwt-decode";
import axios from 'axios';

export default {
    
    data: function(){
        
        return {
            actividades:[],
            loaded: true,
        }
        
    },

    methods: {
        getActividades : function(){
            //alert("entró a getActividdes");

            //axios.get("https://sportclub-be.herokuapp.com/fusionreadall/",{headers:{}})
            axios.get("http://127.0.0.1:8000/fusionreadall/",{headers:{}})

            .then((result) => {
                //alert("entró a result axios fusion read all");
                this.actividades = result.data
                console.log(this.actividades)
                //alert("tamaño arreglo: "+ this.actividades.length);
               /* for(let a of array){
                    this.getInfoActividad(a.actividad,a.horario);
                }*/
                
            })
            .catch(() => {
                alert("Error en getActividades");
            });
        },
        getInfoActividad: async function (act,hor) {
            
            //alert("entró a get info, actividad: "+ act + " horario: "+ hor);

            let token = localStorage.getItem("token_access");
            let userId = jwt_decode(token).user_id.toString();

            let infoAct = {
                nomAct : "",
                capacidad : 0,
                horario : ""
            }

            alert("id_actividad: "+ act + " userId: "+ userId);

            axios.get("http://127.0.0.1:8000/actividadread/"+act+"/", 
            {headers: {'Authorization': `Bearer ${token}`},data:{'id_user':userId}})

            .then((result)=>{
                alert("entró axios activdad read");
                this.infoAct.nomAct = result.data.nombre;
                this.infoAct.capacidad = result.data.capacidad;
            })
            .catch(() => {
                alert("Error en getInfo Act");
            });

            axios.get(`http://127.0.0.1:8000/horariodetail/${hor}/`, 
            {headers: {'Authorization': `Bearer ${token}`}, data:{'id_user':userId}})

            .then((result)=>{
                alert("entró axios horario read");
                this.infoAct.horario = result.data.dia +"-"+result.data.hora;
            })
            .catch(() => {
                alert("Error en getInfo Hor");
            });

            alert("insertar info Act");
            this.actividades.push(infoAct);
            alert("tamaño arreglo: "+ this.actividades.length);
        },
        eliminarAct : function (btn){
            alert("entró a eliminar, value: "+ btn);
           
           let token = localStorage.getItem("token_access");
           let userId = jwt_decode(token).user_id.toString();

           axios.delete(`http://127.0.0.1:8000/fusiondelete/${btn}/`, {
           data: {id_user :userId},
           headers: {'Authorization': `Bearer ${token}`}
           }
           )

           .then((result)=>{
               alert(result);
           })
           .catch((error) => {
                console.log(error)
                alert("ERROR: Fallo en el registro. "+ error );
            });
            
        },
        actualizarAct : function (btn){
            //this.$emit('actualizarActividad', btn);
            localStorage.setItem("idActUpdate", btn);
            this.$router.push({ name: "updateactividad" });
            //bus.$emit('actualizarActividad',btn);
        },
        crearAct: function(){
            this.$router.push({ name: "createactividad" });
        }
    },
    created: function(){
        //alert("entró a created");
        this.getActividades();
    },
}
</script>
