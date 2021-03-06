/**
 * ModuleRegistry.java
 * � MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.localgis.tools.modules;

public interface ModuleRegistry
{
    /**
     * Registra un nuevo módulo resolviendo las instancias existentes.
     * Se sustituye la instancia existente si es una referencia.
     * No se comprueban compatibilidades sólo se registra
     */
public Module register(Module newMod);

}
