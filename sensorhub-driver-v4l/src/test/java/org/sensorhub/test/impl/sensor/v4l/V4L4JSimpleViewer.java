/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is "SensorHub".
 
 The Initial Developer of the Original Code is Sensia Software LLC.
 <http://www.sensiasoftware.com>. Portions created by the Initial
 Developer are Copyright (C) 2013 the Initial Developer. All Rights Reserved.
 
 Please contact Alexandre Robin <alex.robin@sensiasoftware.com> for more 
 information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorhub.test.impl.sensor.v4l;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import au.edu.jcu.v4l4j.FrameGrabber;
import au.edu.jcu.v4l4j.CaptureCallback;
import au.edu.jcu.v4l4j.V4L4JConstants;
import au.edu.jcu.v4l4j.VideoDevice;
import au.edu.jcu.v4l4j.VideoFrame;
import au.edu.jcu.v4l4j.exceptions.StateException;
import au.edu.jcu.v4l4j.exceptions.V4L4JException;

/**
 * This class demonstrates how to perform a simple push-mode capture.
 * It starts the capture and display the video stream in a JLabel
 * @author gilles
 *
 */
public class V4L4JSimpleViewer extends WindowAdapter implements CaptureCallback{
        private static int      width = 640, height = 480, std = V4L4JConstants.STANDARD_WEBCAM, channel = 0;
        private static String   device = "/dev/video0";

        private VideoDevice     videoDevice;
        private FrameGrabber    frameGrabber;

        private JLabel          label;
        private JFrame          frame;



        public static void main(String args[]){

                SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                                new V4L4JSimpleViewer();
                        }
                });
        }

        /**
         * Builds a WebcamViewer object
         * @throws V4L4JException if any parameter if invalid
         */
        public V4L4JSimpleViewer(){
                // Initialise video device and frame grabber
                try {
                        initFrameGrabber();
                } catch (V4L4JException e1) {
                        System.err.println("Error setting up capture");
                        e1.printStackTrace();
                        
                        // cleanup and exit
                        cleanupCapture();
                        return;
                }
                
                // create and initialise UI
                initGUI();
                
                // start capture
                try {
                        frameGrabber.startCapture();
                } catch (V4L4JException e){
                        System.err.println("Error starting the capture");
                        e.printStackTrace();
                }
        }

        /**
         * Initialises the FrameGrabber object
         * @throws V4L4JException if any parameter if invalid
         */
        private void initFrameGrabber() throws V4L4JException{
                videoDevice = new VideoDevice(device);
                frameGrabber = videoDevice.getJPEGFrameGrabber(width, height, channel, std, 80);
                frameGrabber.setCaptureCallback(this);
                width = frameGrabber.getWidth();
                height = frameGrabber.getHeight();
                System.out.println("Starting capture at "+width+"x"+height);
        }

        /** 
         * Creates the UI components and initialises them
         */
        private void initGUI(){
                frame = new JFrame();
                label = new JLabel();
                frame.getContentPane().add(label);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.addWindowListener(this);
                frame.setVisible(true);
                frame.setSize(width, height);       
        }
        
        /**
         * this method stops the capture and releases the frame grabber and video device
         */
        private void cleanupCapture() {
                try {
                        frameGrabber.stopCapture();
                } catch (StateException ex) {
                        // the frame grabber may be already stopped, so we just ignore
                        // any exception and simply continue.
                }

                // release the frame grabber and video device
                videoDevice.releaseFrameGrabber();
                videoDevice.release();
        }

        /**
         * Catch window closing event so we can free up resources before exiting
         * @param e
         */
        public void windowClosing(WindowEvent e) {
                cleanupCapture();

                // close window
                frame.dispose();            
        }


        @Override
        public void exceptionReceived(V4L4JException e) {
                // This method is called by v4l4j if an exception
                // occurs while waiting for a new frame to be ready.
                // The exception is available through e.getCause()
                e.printStackTrace();
        }

        @Override
        public void nextFrame(VideoFrame frame) {
                // This method is called when a new frame is ready.
                // Don't forget to recycle it when done dealing with the frame.
                
                // draw the new frame onto the JLabel
                label.getGraphics().drawImage(frame.getBufferedImage(), 0, 0, width, height, null);
                
                // recycle the frame
                frame.recycle();
        }
}