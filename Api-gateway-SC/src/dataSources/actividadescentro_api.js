const { RESTDataSource } = require('apollo-datasource-rest');

const serverConfig = require('../server');

class ActividadAPI extends RESTDataSource {
    
    constructor() {
        super();
        this.baseURL = serverConfig.actividadesCentro_api_url;
    }

    async createActividad(actividad) {
        actividad = new Object(JSON.parse(JSON.stringify(actividad)));
        return await this.post('/actividad', actividad);
    }

    async actividadesByCentro(username) {
        return await this.get(`/actividades/${username}`);
    }

    async actividadByNombre(nombre) {
        return await this.get(`/actividad/${nombre}`);
    }

    async createHorario(horario) {
        horario = new Object(JSON.parse(JSON.stringify(horario)));
        return await this.post('/horario', horario);
    }

    async horariosByDia(dia) {
        return await this.get(`/horario/${dia}`);
    }

    async getHorarios() {
        return await this.get(`/horarios`);
    }

    async insertCentro(nombre,username) {
        return await this.put(`/actividadcentro/${nombre}`,username);
    }
}

module.exports = ActividadAPI;