package RunLengthDecodingProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/* 
 * Run Length Decoding Problem
 * input : string -> "a3b2k9" , index -> 6
 * input string decodes to : "aaabbkkkkkkkkk"
 * Return the character in the 6th index position
 * output : k
 * 
 * Sol
 * [3,8,10,16,30]
 * index = 11
 */
public class RunLengthDecoding {

	public static void main(String[] args) {
		RunLengthDecoding runLengthDecoding = new RunLengthDecoding();
		int index = 2;
		String ip = "a2b2";
		Character result = runLengthDecoding.findCharByDecodingStr(ip, index);
		if(result != null) {
			System.out.println("Chatacter at index " + index + " = "+ result);
		}else {
			System.out.println("invalid input. Please try again");

		}
	}

	private Character findCharByDecodingStr(String ip, int index) {
		// TODO Auto-generated method stub
		if(ip == null || ip.length() < 2 || index < 0) {
			return null;
		}
		
		String s = "abcdefà";
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		boolean hasNonAlpha = ip.matches("^.*[^a-zA-Z0-9 ].*$");
		boolean hasSpecialChar = p.matcher(ip).find();

		if(hasSpecialChar) {
			System.out.println("has Special char");
			return null;
		}
		PreprocessedInput preprocessedInput = preprocessInput(ip);
		int[] ipIntArr = preprocessedInput.indexesArr;
		
		int low = 0;
		int high = ipIntArr.length-1;
		if(ipIntArr[high] <= index) {
			System.out.println("the val of index u r looking for is out of bounds");
			return null;
		}
		//int opIndex = -1;
		while(high - low > 1) {
			int highVal = ipIntArr[high], lowVal = ipIntArr[low], mid;
			mid = (high + low)/2;
			if(ipIntArr[mid] >= index) {
				//opIndex = mid;
				high = mid;
			}else {
				low = mid;
			}
		}
		return preprocessedInput.charsArr[high];
	}
	
	private PreprocessedInput preprocessInput(String ip) {
		// TODO Auto-generated method stub
		PreprocessedInput preprocessedInput = new PreprocessedInput();
		List<Integer> indexPosArr = new ArrayList<>();
		List<Character> charsArr = new ArrayList<>();
		Character currLetter = null;
		int currIndex = 0;
		for(int i = 0; i < ip.length(); i++) {
			Character c = ip.charAt(i);
			if(Character.isLetter(c)) {
				currLetter = c;
			}else {
				String subStr = ip.substring(i, ip.length());
				int num1 = getNumberForCurrChar(subStr);
				int length = (int) (Math.log10(num1) + 1);	
				i += length-1;
				charsArr.add(currLetter.charValue());
				currIndex += num1;
				indexPosArr.add(currIndex);
			}
		}
		
		preprocessedInput.indexesArr = indexPosArr.stream()
				  .mapToInt(Integer::intValue)
				  .toArray();		
		preprocessedInput.charsArr = charsArr.toArray(new Character[charsArr.size()]);
		return preprocessedInput;
	}



	private int getNumberForCurrChar(String subStr) {
		// TODO Auto-generated method stub
		String numStr = "";
		for(int i = 0; i < subStr.length(); i++) {
			
			Character c = subStr.charAt(i);
			if(Character.isDigit(c)) {
				numStr += c;
			}else {
				break;
			}
		}
		int num = Integer.parseInt(numStr);
		return num;
	}



	private class PreprocessedInput{
		int[] indexesArr;
		Character[] charsArr;
	}
}
