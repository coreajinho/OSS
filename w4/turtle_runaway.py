import tkinter as tk
import turtle, random

class RunawayGame:
    def __init__(self, canvas, runner,runner2, chaser, catch_radius=50):
        self.canvas = canvas
        self.runner = runner
        self.runner2 = runner2
        self.chaser = chaser
        self.catch_radius2 = catch_radius**2
        self.time = 0
        self.score = 0

        # Initialize 'runner' and 'chaser'
        self.runner.shape('turtle')
        self.runner.color('blue')
        self.runner.penup()

        self.runner2.shape('turtle')
        self.runner2.color('yellow')
        self.runner2.penup()

        self.chaser.shape('turtle')
        self.chaser.color('red')
        self.chaser.penup()

        # Instantiate another turtle for drawing
        self.drawer = turtle.RawTurtle(canvas)
        self.drawer.hideturtle()
        self.drawer.penup()

        self.time_drawer = turtle.RawTurtle(canvas)
        self.time_drawer.hideturtle()
        self.time_drawer.penup()

        self.score_drawer = turtle.RawTurtle(canvas)
        self.score_drawer.hideturtle()
        self.score_drawer.penup()

    def is_catched(self):
        p = self.runner.pos()
        q = self.chaser.pos()
        dx, dy = p[0] - q[0], p[1] - q[1]
        return dx**2 + dy**2 < self.catch_radius2
    
    def is_catched2(self):
        p = self.runner2.pos()
        q = self.chaser.pos()
        dx, dy = p[0] - q[0], p[1] - q[1]
        return dx**2 + dy**2 < self.catch_radius2

    def start(self, init_dist=400, ai_timer_msec=100):
        self.runner.setpos((-init_dist / 2, 0))
        self.runner.setheading(0)
        self.runner2.setpos((-init_dist / 2, 100))
        self.runner2.setheading(30)
        self.chaser.setpos((+init_dist / 2, 0))
        self.chaser.setheading(180)

        self.drawer.penup()
        self.drawer.setpos(-300, 300)
        self.drawer.write(f'Is catched? easy one:False, hard one:False')

        self.time_drawer.penup()
        self.time_drawer.setpos(300, 300)
        self.time_drawer.write(f'Time: {self.time}')

        self.score_drawer.penup()
        self.score_drawer.setpos(300, 250)
        self.score_drawer.write(f'Score: {self.score}')

        self.ai_timer_msec = ai_timer_msec
        self.canvas.ontimer(self.step, self.ai_timer_msec)
        self.canvas.ontimer(self.timer, 1000)

    def timer(self):
        self.time_drawer.undo()
        self.time_drawer.penup()
        self.time_drawer.setpos(300,300)
        self.time_drawer.write(f'time: {self.time}')
        self.time += 1
        self.canvas.ontimer(self.timer, 1000)

    def step(self):
        self.runner.run_ai(self.chaser.pos(), self.chaser.heading())
        self.runner2.run_ai(self.chaser.pos(), self.chaser.heading())
        self.chaser.run_ai(self.runner.pos(), self.runner.heading())

        is_catched = self.is_catched()
        is_catched2 = self.is_catched2()
        self.drawer.undo()
        self.drawer.penup()
        self.drawer.setpos(-300, 300)
        self.drawer.write(f'Is catched? easy one:{is_catched}, hard one:{is_catched2}')

        if(is_catched == True):
            self.score +=1
        if(is_catched2 == True):
            self.score +=5

        self.score_drawer.undo()
        self.score_drawer.penup()
        self.score_drawer.setpos(300,250)
        self.score_drawer.write(f'score:{self.score}')

        self.canvas.ontimer(self.step, self.ai_timer_msec)

class ManualMover(turtle.RawTurtle):
    def __init__(self, canvas, step_move=10, step_turn=10):
        super().__init__(canvas)
        self.step_move = step_move
        self.step_turn = step_turn

        # Register event handlers
        canvas.onkeypress(lambda: self.forward(self.step_move), 'Up')
        canvas.onkeypress(lambda: self.backward(self.step_move), 'Down')
        canvas.onkeypress(lambda: self.left(self.step_turn), 'Left')
        canvas.onkeypress(lambda: self.right(self.step_turn), 'Right')
        canvas.listen()

    def run_ai(self, opp_pos, opp_heading):
        pass

class RandomMover(turtle.RawTurtle):
    def __init__(self, canvas, step_move=10, step_turn=10):
        super().__init__(canvas)
        self.step_move = step_move
        self.step_turn = step_turn

    def run_ai(self, opp_pos, opp_heading):
        mode = random.randint(0, 2)
        if mode == 0:
            self.forward(self.step_move)
        elif mode == 1:
            self.left(self.step_turn)
        elif mode == 2:
            self.right(self.step_turn)

if __name__ == '__main__':
    # Use 'TurtleScreen' instead of 'Screen' to prevent an exception from the singleton 'Screen'
    root = tk.Tk()
    canvas = tk.Canvas(root, width=700, height=700)
    canvas.pack()
    screen = turtle.TurtleScreen(canvas)

    runner = RandomMover(screen)
    runner2 = RandomMover(screen,30,30)
    chaser = ManualMover(screen)

    game = RunawayGame(screen, runner, runner2 ,chaser)
    game.start()
    screen.mainloop()
