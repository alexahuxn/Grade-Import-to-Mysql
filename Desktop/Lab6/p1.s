# /**
#  * Given a table of recipes and an inventory of items, this function
#  * will populate times_craftable with the number of times each recipe
#  * can be crafted.
#  *
#  * Note: When passing arrays as parameters, the register $a0 will hold the starting
#  * address of the array, not the contents of the array.
#  */

# void craftable_recipes(int inventory[5], int recipes[10][5], int times_craftable[10]) {
#     const int NUM_ITEMS = 5;
#     const int NUM_RECIPES = 10;

#     for (int recipe_idx = 0; recipe_idx < NUM_RECIPES; recipe_idx++) {
#         times_craftable[recipe_idx] = 0x7fffffff;  // Largest positive number
#         int assigned = 0;

#         for (int item_idx = 0; item_idx < NUM_ITEMS; item_idx++) {
#             if (recipes[recipe_idx][item_idx] > 0) {
#                 // If the item is not required for the recipe, skip it
#                 // Note: There is a div psuedoinstruction to do the division
#                 // The format is:
#                 //    div   $rd, $rs, $rt
#                 int times_item_req = inventory[item_idx] / recipes[recipe_idx][item_idx];
#                 if (times_item_req < times_craftable[recipe_idx]) {
#                     times_craftable[recipe_idx] = times_item_req;
#                     assigned = 1;
#                 }
#             }
#         }

#         if (assigned == 0) {
#             times_craftable[recipe_idx] = 0;
#         }
#     }
# }

.globl craftable_recipes
craftable_recipes:
        sub $sp, $sp, 20
        sw $s0, 0($sp)
        sw $s1, 4($sp)
        sw $s2, 8($sp)
        sw $s3, 16($sp)
        
        li $t0, 0               # item_idx = 0
        li $t1, 0               # recipe_idx = 0
        li $s1, 5               # num_items = 5
        li $s2, 10              # num_recipes = 10
        # $a0 = inventory; $a1 = recipes; $a2 = time_craftable 

for:
        bge $t1, $s2, if        # if recipe_idx < num_Recipes
        add $t3, $a2, $t1       # address of times_craftable[recipe_indx]
        addi $t3, $t3, 0x7fffffff
        li $t4, 0               # assigned = 0

        for1:
                bge $t0, $s1, if3       # whether item_idx < num_items
                
                if1:
                        add $t5, $a1, $t1        # recipe[recipe_idx] at $t5
                        add $t5, $t5, $t0        # recipe[recipe_idx][item_idx] at $t5
                        bge $t5, $0, for1      # if recipe[recipe_idx][item_idx] >= 0 -> for1
                        #  If the item is not required for the recipe, skip it

                        add $t2, $a0, $t0       # iventory[item_idx] at $t2
                        div $t6, $t2, $t5      # times_item_req at $t10, times_item_req = inventory[item_idx] / recipes[recipe_idx][item_idx]
                        
                        if2:
                                bge $t6, $t3, for1              #if time_item_req < time_craftable[recipe_indx]
                                add $t3, $t6, $0                # times_craftable[recipe_idx] = times_item_req;
                                addi $t4, $t4, 1                # assigned = 1
                
                addi $t0, $t0, 1                # item_indx++
                j for1

        if3: 
                bne $t4, 0, end
                li $t3, 0
        
        addi $t1, $t1, 1                # recipe_indx ++
        j for

        

end:
        jr      $ra