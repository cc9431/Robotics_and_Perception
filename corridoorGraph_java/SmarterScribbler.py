'''
Charlie Calder and Elias Posen
  
Scribbler 2 control system:
  Keeps track of poition of robot according to odometer "clicks"
  Can make robot rotate and straight line to any given point from any given point

Scribbler notes:
    chassie size = 14.5 cm
    wheel diameter = 8cm
    wheel circumference = 25.13cm
    distance after 1 rotation = 25cm or 250mm
    wheel clicks per rotation = 997 to 1008 clicks
    4.06 clicks/mm
    1900 clicks per 360 degrees, stationary rotation
    7585, 3783 clicks per 360 degrees, arc with motors(.25,.5)
    (95/18) clicks per degrees
'''

from Myro import *
from Graphics import*
import math

class SmarterScribbler:
    
    SPEED = .3
    CLICKSMM = 4.06 #clicks per mm
    CLICKSDEG = 5.45
    CLICKSDEG360 = 4.8
    CLICKSDEG270 = 4.45
    CLICKSDEG180 = 4.2
    CLICKSDEG90 = 2.9
    SIZE_W = 2048
    SIZE_H = 1536
    
    def __init__ (self, regular_scribbler_object):
        self.wall = False
        self.win = Window("SmarterScribbler", SmarterScribbler.SIZE_W, SmarterScribbler.SIZE_H)
        self.win.setBackground(Color('black'))
        self.robot = regular_scribbler_object #makeRobot(comport, scribbler)
        self.robot.getEncoders(True) #Reseting encoders to [0,0]
        self.gx = 0 #global x-coordinate in mm
        self.gy = 0 #global y-coordinate in mm
        self.t = 0 #global theta (direction in relation to the global plane, going right along the x axis implies theta = 0) in degrees
        
    def x(self): #returns global x coordinate
        return self.gx
    def y(self): #returns global y coordinate
        return self.gy
    def theta(self): #returns global theta
        return self.t
    def reset(self):
        self.goto(0,0)
        self.rotateTo(0)
    
    def forward(self, mm):
        print(mm)
        getEncoders(True)
        count = 0
        lx = self.x()
        ly = self.y()
        delta_x = math.cos(math.radians(self.t))*(mm)
        delta_y = math.sin(math.radians(self.t))*(mm)
        self.gx += delta_x
        self.gy += delta_y
        
        if mm<0: #go backwards
            self.robot.motors(-SmarterScribbler.SPEED,-SmarterScribbler.SPEED)
        else:
            self.robot.motors(SmarterScribbler.SPEED,SmarterScribbler.SPEED)
        wait(.25)
        while True: 
            delta = getEncoders(True)
            count = count + ((abs(delta[0])+abs(delta[1]))/2)
            if count > abs(mm*SmarterScribbler.CLICKSMM):
                self.robot.stop()
                break
                
    def left(self, deg): #origin is in top left of window    
        print(deg)
        self.t = (self.t - deg)%360
        count = 0
        if deg == 90:
            self.robot.motors(-0.25, 0.25, 2.7)
            return
        elif deg <0: #turn right
            self.robot.motors(SmarterScribbler.SPEED,-SmarterScribbler.SPEED)
        else:
            self.robot.motors(-SmarterScribbler.SPEED,SmarterScribbler.SPEED)
        wait(.25)
        while True:    
            delta = getEncoders(True)
            count = count + abs(delta[0])
            if count> abs(deg*SmarterScribbler.CLICKSDEG):
                self.robot.stop()
                break
       
    def goto(self,x,y):
        if self.gx< x: # when the robot is 'left' of the desired endpoint
            if self.gy<y: #when robot is left and below endpoint
                desired_t = math.degrees(math.atan((y-self.gy)/(x-self.gx)))
                self.rotateTo(desired_t)
                self.forward((y-self.gy)/math.sin(math.radians(self.t)))
            elif self.gy>y: #when robot is left and above endpoint
                temp_t = math.degrees(math.atan((self.gy-y)/(x-self.gx)))
                desired_t = 360-temp_t
                self.rotateTo(desired_t)
                self.forward((self.gy-y)/math.sin(math.radians(temp_t)))
            else: #if robot is left and at same y-coordinate as endpoint
                if self.t == 0:
                    print("working")
                    self.forward(x-self.gx)
                else:
                    self.rotateTo(0)
                    self.forward(x-self.gx)
            
        elif self.gx>x: #when the robot is 'right' of the desired endpoint
            if self.gy<y: #when robot is right and below endpoint
                desired_t = 180 - math.degrees(math.atan((y-self.gy)/(self.gx-x)))
                self.rotateTo(desired_t)
                self.forward((y-self.gy)/math.sin(math.radians(self.t)))
            elif self.gy>y: #when robot is right and above endpoint  
                temp_t = math.degrees(math.atan((self.gx-x)/(self.gy-y)))
                desired_t = 270 - temp_t
                self.rotateTo(desired_t)
                self.forward((self.gx-x)/math.sin(math.radians(temp_t)))     
            else: #if robot is right and at same y-coordinate as endpoint
                self.rotateTo(180)
                self.forward(self.gx-x)
                
        else: #robot and endpoint have the same x-cooridinate -> self.theta must equal 90 or 270
            if y>self.gy:
                desired_t = 90
            elif y<self.gy:
                desired_t = 270
            else:
                print("You are already there stupid")
                return #end the function
            self.rotateTo(desired_t)
            self.forward(abs(y-self.gy))
     
    def rotateTo(self, d_t): #rotate robot until it reaches a specified theta
        self.left(-(360-self.t + d_t)%360)
                           
    def motors(self, l, r, sec):
        lx = self.x()
        ly = self.y()

        self.robot.getEncoders(True)
        self.robot.motors(l,r)
        for t in timer(sec):
            delta = getEncoders(True)
            self.updateT(delta)
            new = self.updatePos(delta)
            
            self.drawLine(lx,ly,new[0],new[1])
            lx = new [0]
            ly = new[1]
            
        self.robot.stop()
        
    def updatePos(self, delta): #Updates postion based on odometer
        delta_xr = delta[0]/SmarterScribbler.CLICKSMM
        delta_xl = delta[1]/SmarterScribbler.CLICKSMM
        
        delta_xi = math.cos(math.radians(self.t))*((delta_xr+delta_xl)/2)
        delta_yi = math.sin(math.radians(self.t))*((delta_xr+delta_xl)/2)
        
        self.gx = self.gx + delta_xi
        self.gy = self.gy + delta_yi
        
        return [self.x(),self.y()]
            
    def updateT(self,delta):
               
        delta_t = -1*((((delta[0]-delta[1])/2)/SmarterScribbler.CLICKSDEG)%360)
        self.t= (self.t + delta_t)%360
        
    
    def drawLine(self, x1,y1,x2,y2): #(x1,y1) is the starting point, (x2,y2) is the end point
        l= Line((x1,y1),(x2,y2))
        l.border = 3
        l.color = Color('white')
        l.draw(self.win)

#Reads in cooridinates from pathe_directions.txt which is written to by corridoorGraph
def path():
    bum = makeRobot("Scribbler","/dev/tty.Fluke2-0D01-Fluke2")
    r = SmarterScribbler(bum)
    print('robot is ready')
    wait(5)
    f = open("path_directions.txt", "r")
    cnt = 0
    for command in f:
        if cnt == 0:
            r.gx = int(command)
        elif cnt == 1:
            r.gy = int(command)
        else:
            if cnt % 2 == 0:
                x = int(command)
            else:
                print(x, command)
                r.goto(x, int(command))
        cnt += 1


path()