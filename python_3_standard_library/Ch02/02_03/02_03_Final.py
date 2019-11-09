# Random Module
import random

# Random Numbers
print(random.random())
# randrange is exclusive, so the possible values are 0 and 1 in this case
decider = random.randrange(2)
if decider == 0:
    print("HEADS")
else:
    print("TAILS")
print(decider)

# If a start value is provided to randrange, this start value is inclusive
print(f"You rolled a {random.randrange(1, 7)}")

# Random Choices
lotteryWinners = random.sample(range(100), 5)
print(lotteryWinners)

possiblePets = ["cat", "dog", "fish"]
print(random.choice(possiblePets))

cards = ["Jack", "Queen", "King", "Ace"]
random.shuffle(cards)
print(cards)
