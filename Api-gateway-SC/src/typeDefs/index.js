//Se llama al typedef (esquema) de cada submodulo
const authTypeDefs = require('./auth_type_defs');
const horarioTypeDefs = require('./horario_type_defs');
const actividadTypeDefs = require('./actividad_type_defs');

//Se unen
const schemasArrays = [authTypeDefs, horarioTypeDefs, actividadTypeDefs];

//Se exportan
module.exports = schemasArrays;