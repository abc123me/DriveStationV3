package net.net16.jeremiahlowe.drivestation3;

import com.fazecast.jSerialComm.SerialPort;

public interface Enums {
	public static enum OS{
		Windows, Mac, Linux, Solaris, Unknown;
		public static final OS getOS(){
			String on = System.getProperty("os.name").toLowerCase();
			if(on.contains("linux")) return Linux;
			if(on.contains("windows")) return Windows;
			if(on.contains("mac")) return Mac;
			if(on.contains("solaris")) return Solaris;
			System.err.println("couldn't detect os! os.name=" + on);
			return OS.Unknown;
		}
	}
	public static enum Parity{ 
		None, Odd, Even, Space, Mark;
		public int toParity(){
			if(this == None) return SerialPort.NO_PARITY;
			else if(this == Odd) return SerialPort.ODD_PARITY;
			else if(this == Even) return SerialPort.EVEN_PARITY;
			else if(this == Space) return SerialPort.SPACE_PARITY;
			else if(this == Mark) return SerialPort.MARK_PARITY;
			else return -1;
		}
	}
	public static enum StopBits{ 
		$1_bit, $2_bit, $1_5_bit;
		public int toStopBits(){
			if(this == $1_bit) return SerialPort.ONE_STOP_BIT;
			else if(this == $2_bit) return SerialPort.TWO_STOP_BITS;
			else if(this == $1_5_bit) return SerialPort.ONE_POINT_FIVE_STOP_BITS;
			else return -1;
		}
		@Override
		public String toString(){
			if(this == $1_5_bit) return "1.5 bit";
			String out = super.toString();
			out = out.substring(1);
			out = out.replaceAll("_", " ");
			return out;
		}
	}
	public static enum DataBits{
		$8_bit, $7_bit, $6_bit, $5_bit;
		public int toDataBits(){
			if(this == $5_bit) return 5;
			else if(this == $6_bit) return 6;
			else if(this == $7_bit) return 7;
			else if(this == $8_bit) return 8;
			else return -1;
		}
		@Override
		public String toString(){
			String out = super.toString();
			out = out.substring(1);
			out = out.replaceAll("_", " ");
			return out;
		}
	}
	public static enum Baudrate{
		$300, $2_400, $4_800, $9_600, $19_200, $38_400, $57_600, $74_880, $115_200, $230_400, $250_000;
		public int toBaudrate(){
			return Integer.parseInt(this.toString().replaceAll(",", ""), 10);
		}
		@Override
		public String toString(){
			String out = super.toString();
			out = out.substring(1);
			out = out.replaceAll("_", ",");
			return out;
		}
	}
	public static enum LineSeperator{
		None, NL, CR, NL_CR, CR_NL;
		public String toSuffix(){
			switch(this){
				case None: return "";
				case NL: return "\n";
				case CR: return "\r";
				case NL_CR: return "\n\r";
				case CR_NL: return "\r\n";
			}
			return "";
		}
	}
	public static enum Motor{A, B, Both}
	public static enum Speed{Forward, Stop, Backward}
}
