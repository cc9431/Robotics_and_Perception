# Robotics and Perception

Must be run through Processing3.
To view the effects of the project, open and run corridoorGraph_java.pde. You might need to install the processing.video library but to do that simply go to Sketch > Import Library > Add Library >. The program generates 2 images (test0_path.jpg and test0_staircase_path.jpg) and a text document with coordinates for the robot from the input image "test0.jpg".
Due to inconsistencies in the lighting of our testing room, we had to create a helper function (find_color.pde) that allows us to gather the appropriate RGB information for each image. find_color then writes path_colors .txt which corridoorGraph_java.pde reads from. This is already done for the text image so it is just for your understanding. For more detailed information on the goals, implementation, and self-evaluation of this project, please refer to the project report (CalderPosenProjectReport.pdf).
