set PUSH, mod_colorshift
set PUSH, mod_hello
set [task_list], SP
set PUSH, 2
:sched_task_run
    set A, [task_list]
    add A, [task]
    set PC, [A]
:sched_advance
    add [task], 1
    set A, [task_list]
    sub A, 1 ; Get task count in A
    ife [A], [task]
        set [task], 0
    set PC, sched_task_run
:yield
    set X, POP ; Get task PC
    set A, [task_list]
    add A, [task]
    set [A], X ; Overwrite task slot with current pc
    set PC, sched_advance
; System Variables
:task
dat 0
:task_list
dat 0
:putc_line
dat 0
:putc_col
dat 0
:newline
dat "\n"
:putc_color
dat 0xf100

; Terminal Subroutines
; Put char in reg A to screen
:putc
    set PUSH, B
    ife A, [newline]
        set PC, putc_newline
    bor A, [putc_color]
    set B, [putc_line]
    mul B, 32
    add B, [putc_col]
    add B, 0x8000
    set [B], A
    add [putc_col], 1
    ifg 32, [putc_col]
        set PC, putc_end
:putc_newline
    add [putc_line], 1
    set [putc_col], 0
    ifg 16, [putc_line]
        set PC, putc_end
    ; We need to scroll!
    jsr scroll
    set [putc_line], 16
:putc_end
    set B, POP
    set PC, POP ; return
    ; Scroll the terminal up one line
    ; Each line is 32 words
:scroll
    set PUSH, I ; Index in video ram
    set PUSH, A ; Index of thing we're copying
    set I, 0x8000
:scroll_loop
    set A, I
    add A, 32 ; Corresponding character on next line
    ifg A, 0x8400 ; End of video ram
        set PC, scroll_end ; return
    set [I], [A] ; Copy back 32 words
    add I, 1
    set PC, scroll_loop
:scroll_end
    set A, POP
    set I, POP
    set PC, POP ; return
:mod_colorshift
add [putc_color], 0x100
jsr yield
SET PC, mod_colorshift
:mod_hello
set [mod_hello_itr], 0
:mod_hello_loop
set I, [mod_hello_itr]
add I, mod_hello_hwstr
set A, [I]
ife A, 0
    set PC, mod_hello_end
jsr putc
add [mod_hello_itr], 1
set PC, mod_hello_loop
:mod_hello_end
jsr yield
set PC, mod_hello
:mod_hello_hwstr
    dat "Hey everybody, how is it going in DCPU16 land today?\n", 0
:mod_hello_itr
    dat 0


