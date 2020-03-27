/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2019 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorhub.api.datastore;

import java.util.Optional;
import org.sensorhub.api.datastore.IDataStreamStore.DataStreamInfoField;


/**
 * <p>
 * Generic interface for managing data streams within an observation store.<br/>
 * Removal operations also remove all observations associated to a data stream. 
 * </p>
 *
 * @author Alex Robin
 * @date Sep 18, 2019
 */
public interface IDataStreamStore extends IDataStore<Long, IDataStreamInfo, DataStreamInfoField, DataStreamFilter>
{
    
    public static class DataStreamInfoField extends ValueField
    {
        public static final DataStreamInfoField PROCEDURE_ID = new DataStreamInfoField("procedureID");
        public static final DataStreamInfoField OUTPUT_NAME = new DataStreamInfoField("outputName");
        public static final DataStreamInfoField RECORD_VERSION = new DataStreamInfoField("recordVersion");
        public static final DataStreamInfoField RECORD_DESCRIPTION  = new DataStreamInfoField("recordDescription");
        public static final DataStreamInfoField RECORD_ENCODING = new DataStreamInfoField("recordEncoding");
        
        public DataStreamInfoField(String name)
        {
            super(name);
        }
    }
    
    
    /**
     * Add a new data stream and generate a new unique key for it.
     * @param dsInfo The data stream info object to be stored
     * @return The key associated with the new data stream
     */
    public Long add(IDataStreamInfo dsInfo);
    
    
    /**
     * Helper method to retrieve the internal ID of the latest version of the
     * data stream corresponding to the specified procedure and output.
     * @param procUID Unique ID of procedure producing the data stream
     * @param outputName Name of output generating the data stream
     * @return The feature key or null if none was found with this UID
     */
    public default Long getLatestVersionKey(String procUID, String outputName)
    {
        Entry<Long, IDataStreamInfo> e = getLatestVersionEntry(procUID, outputName);
        return e != null ? e.getKey() : null;
    }
    
    
    /**
     * Helper method to retrieve the latest version of the data stream
     * corresponding to the specified procedure and output.
     * @param procUID Unique ID of procedure producing the data stream
     * @param outputName Name of output generating the data stream
     * @return The feature representation or null if none was found with this UID
     */
    public default IDataStreamInfo getLatestVersion(String procUID, String outputName)
    {
        Entry<Long, IDataStreamInfo> e = getLatestVersionEntry(procUID, outputName);
        return e != null ? e.getValue() : null;
    }
    
    
    /**
     * Helper method to retrieve the entry for the latest version of  the
     * data stream corresponding to the specified procedure and output.
     * @param procUID Unique ID of procedure producing the data stream
     * @param outputName Name of output generating the data stream
     * @return The feature entry or null if none was found with this UID
     */
    public default Entry<Long, IDataStreamInfo> getLatestVersionEntry(String procUID, String outputName)
    {
        Optional<Entry<Long, IDataStreamInfo>> entryOpt = selectEntries(new DataStreamFilter.Builder()
            .withProcedures(procUID)
            .withOutputNames(outputName)
            .build())
        .findFirst();
        
        return entryOpt.isPresent() ? entryOpt.get() : null;
    }
    
}
