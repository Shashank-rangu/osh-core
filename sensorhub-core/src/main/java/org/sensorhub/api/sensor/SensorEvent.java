/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
The Initial Developer is Sensia Software LLC. Portions created by the Initial
Developer are Copyright (C) 2014 the Initial Developer. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorhub.api.sensor;

import org.sensorhub.api.common.Event;


/**
 * <p>
 * Simple base data structure for all events generated by sensors (or their drivers)
 * </p>
 *
 * <p>Copyright (c) 2010</p>
 * @author Alexandre Robin
 * @since Nov 5, 2010
 */
public class SensorEvent extends Event
{
	private static final long serialVersionUID = -1273002175078885567L;

	
	/**
	 * Possible event types for a SensorEvent
	 */
    public enum Type
	{
		ACTIVATED,
		DEACTIVATED,
		CONNECTED,
		DISCONNECTED,
		NEW_DATA_AVAILABLE,
		COMMAND_STATUS,
		SENSOR_CHANGED
	};
	
	
	/**
	 * ID of sensor that generated the event
	 */
	protected String sensorId;
	
	
	/**
	 * Type of sensor event
	 */
	protected Type type;
	
	
	/**
	 * Sole constructor
	 * @param sensorId ID of originating sensor
	 * @param timeStamp time of event generation
	 * @param type type of event
	 */
	public SensorEvent(double timeStamp, String sensorId, Type type)
	{
	    this.sensorId = sensorId;
	    this.timeStamp = timeStamp;
	    this.type = type;
	}


    public String getSensorId()
    {
        return sensorId;
    }


    public Type getType()
    {
        return type;
    }
}
