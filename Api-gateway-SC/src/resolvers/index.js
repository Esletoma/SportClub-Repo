const horarioResolver = require('./horario_resolver');
const actividadResolver = require('./actividad_resolver');
const authResolver = require('./auth_resolver');
const lodash = require('lodash');
const resolvers = lodash.merge(horarioResolver, actividadResolver, authResolver);
module.exports = resolvers;