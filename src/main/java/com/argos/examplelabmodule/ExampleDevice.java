/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argos.examplelabmodule;

import com.argos.controlx.Modbus.FPackage;
import com.argos.controlx.Modbus.MbEvent;
import com.argos.labgui.gui.perspective.Perspective;
import com.argos.labgui.gui.unit.Unit;
import com.argos.labmaster.module.DeviceBehavior;
import com.z.statemachine.StateFMS;
import java.awt.Color;


/**
 *
 * @author pavel
 */
public class ExampleDevice extends DeviceBehavior{

    private byte[] packOk;
    private byte[] packError;
    private final int slaveAddress;
    private final Perspective persp;
    
    public ExampleDevice(int slaveAddress, Perspective p) {
        super(slaveAddress, 0);
        this.slaveAddress = slaveAddress;
        
        FPackage fp = new FPackage();
        
        
        packOk = fp.readHoldingRegisters(slaveAddress, 0x2, 0x2);
        packError = fp.readHoldingRegisters(slaveAddress, 0x2, 0x2);
        
        oKsend0x1.setSendPackage(packOk);
        timeOutsend0x1.setSendPackage(packError);
        errCRCsend0x1.setSendPackage(packError);
        errCRCsend0x1.setSendPackage(packError);
        
        
        persp = p;
        addRule(oKsend0x1, MbEvent.MbStatus.OK);
        addRule(timeOutsend0x1, MbEvent.MbStatus.TIME_OUT);
        addRule(errCRCsend0x1, MbEvent.MbStatus.ERROR_CRC);
        addRule(errCRCsend0x1, MbEvent.MbStatus.DATA_CORRUPTION);
        
        
    }
    static int countAddress = 0x1;
    
    StateFMS oKsend0x1 = new StateFMS() {
        
        @Override
        public void doingDo() {
            ((Unit)persp.getUnit(countAddress)).setBackground(Color.green);
            FPackage fp = new FPackage();
            if(++countAddress > 3){
                countAddress = 1;
            }
            packOk = fp.readHoldingRegisters(countAddress, 0x2, 0x2);
            setSendPackage(packOk);
        }
    };
    
    StateFMS timeOutsend0x1 = new StateFMS() {
        
        @Override
        public void doingDo() {
            ((Unit)persp.getUnit(slaveAddress)).setBackground(Color.red);
            FPackage fp = new FPackage();
            if(++countAddress > 3){
                countAddress = 1;
            }
            packOk = fp.readHoldingRegisters(countAddress, 0x2, 0x2);
            setSendPackage(packOk);
        }
        
    };
    
    StateFMS errCRCsend0x1 = new StateFMS() {
        
        @Override
        public void doingDo() {
            
            ((Unit)persp.getUnit(slaveAddress)).setBackground(Color.PINK);
        }
    };
    
}
