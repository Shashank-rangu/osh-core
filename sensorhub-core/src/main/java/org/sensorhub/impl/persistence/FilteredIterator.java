/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2017 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorhub.impl.persistence;

import java.util.Iterator;


/**
 * <p>
 * Helper class to write iterators that filter elements returned by another
 * iterator on-the-fly
 * </p>
 *
 * @author Alex Robin
 * @param <E> 
 * @since Mar 15, 2017
 */
public abstract class FilteredIterator<E> extends IteratorWrapper<E, E>
{
    
    public FilteredIterator(Iterator<E> it)
    {
        super(it);
    }
    
    
    @Override
    protected E process(E elt)
    {
        if (accept(elt))
            return elt;
        return null;
    }
    
    
    protected abstract boolean accept(E elt);

}
