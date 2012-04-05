package br.com.leandromoreira.jdcpu16br;

public class OpCodes {

    public static final int NOT_BASIC = 0x00; // Not basic operation
    public static final int SYSCALL_JSR = 0x01; // Not basic operation
    public static final int SET = 0x01; //: SET a, b - sets a to b
    public static final int ADD = 0x02; //: ADD a, b - sets a to a+b, sets O to 0x0001 if there's an overflow, 0x0 otherwise
    public static final int SUB = 0x03; //: SUB a, b - sets a to a-b, sets O to 0xffff if there's an underflow, 0x0 otherwise
    public static final int MUL = 0x04; //: MUL a, b - sets a to a*b, sets O to ((a*b)>>16)&0xffff
    public static final int DIV = 0x05; //: DIV a, b - sets a to a/b, sets O to ((a<<16)/b)&0xffff. if b==0, sets a and O to 0 instead.
    public static final int MOD = 0x06; //: MOD a, b - sets a to a%b. if b==0, sets a to 0 instead.
    public static final int SHL = 0x07; //: SHL a, b - sets a to a<<b, sets O to ((a<<b)>>16)&0xffff
    public static final int SHR = 0x08; //: SHR a, b - sets a to a>>b, sets O to ((a<<16)>>b)&0xffff
    public static final int AND = 0x09; //: AND a, b - sets a to a&b
    public static final int BOR = 0x0A; //: BOR a, b - sets a to a|b
    public static final int XOR = 0x0B; //: XOR a, b - sets a to a^b
    public static final int IFE = 0x0C; //: IFE a, b - performs next instruction only if a==b
    public static final int IFN = 0x0D; //: IFN a, b - performs next instruction only if a!=b
    public static final int IFG = 0x0E; //: IFG a, b - performs next instruction only if a>b
    public static final int IFB = 0x0F; //: IFB a, b - performs next instruction only if (a&b)!=0     
}
