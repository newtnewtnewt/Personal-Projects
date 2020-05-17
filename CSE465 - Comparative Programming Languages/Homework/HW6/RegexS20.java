import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Fill this code in.
 * All of your code must reside in this file.
 * Use regular expressions to solve these problems.
 * Do not change the names of the class or methods.
 * You can add methods static methods to this class.
 */

public class RegexS20 {
	/*
	 * 3 digits, 3 digits, and 4 digit. Each pair, separated by a single dash.
	 * No intervening spaces.
	 */
	public static boolean isLongPhoneNumber(String str) {
		String regexString = "[0-9]{3}-[0-9]{3}-[0-9]{4}";
		Pattern p = Pattern.compile(regexString);
		Matcher m = p.matcher(str);
		return m.matches();
		
	}
	/*
	 * Same as above, but the 3 digit blocks start with a 0 or 1,
	 */
	public static boolean isLongPhoneNumber2(String str) {
		String regexString = "[2-9][0-9]{2}-[2-9][0-9]{2}-[0-9]{4}";
		Pattern p = Pattern.compile(regexString);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	/*
	 * The C# definition of real_literal three parts to it.
	 * http://www.externsoft.ch/download/csharp.html#real_literal
	 * The first of three parts looks like:
	 * [ decimal_digits ] "." decimal_digits [ exponent_part ] [ real_type_suffix ]
	 * It is this part that you are to recognize.
	 */
	public static boolean isRealLiteralPart1(String str) {
		String regexString = "[-+]?[0-9]*.[0-9]+([eE][-+]?[0-9]+)?[fFdDmM]?";
		Pattern p = Pattern.compile(regexString);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	/*
	 * Determines if string constitutes a legal email address.
	 * Use the following for the definition:
	 * https://help.returnpath.com/hc/en-us/articles/220560587-What-are-the-rules-for-email-address-syntax-
	 */
	public static boolean isEmailAddress(String str) {
		String recipientName = "([A-Za-z0-9][A-Za-z0-9!#\\$%\\&'+-/=\\?\\^_`{|]{0,62}[A-Za-z0-9]|[A-Za-z0-9])";
		String atSymbol = "@";
		String domainName = "[A-Za-z0-9][A-Za-z0-9\\-\\.]{0,255}";
		String topLevelDomain = "\\.[a-z]+";
		String regexString = recipientName + atSymbol + domainName + topLevelDomain;
		String dupeChecker = "^(?!.*[!#\\$%\\&'+-/=\\?\\^_`{|]{2}).+$";
		Pattern p = Pattern.compile(regexString);
		Pattern p2 = Pattern.compile(dupeChecker);
		Matcher m = p.matcher(str);
		Matcher m2 = p2.matcher(str);
		return m.matches() && m2.matches();
	}
	public static void main(String [] args) {
		System.out.println(isLongPhoneNumber("513-529-1809"));		// true
		System.out.println(isLongPhoneNumber(" 513-529-1809 "));	// false
		System.out.println(isLongPhoneNumber("513 529 1809"));		// false
		System.out.println(isLongPhoneNumber("013-529-1809"));		// true

		System.out.println(isLongPhoneNumber2("513-529-1809"));		// true
		System.out.println(isLongPhoneNumber2("513-029-1809"));		// false
		System.out.println(isLongPhoneNumber2("013-529-1809"));		// false
		System.out.println(isLongPhoneNumber2("013-029-1809"));		// false
		
		System.out.println(isRealLiteralPart1("12.0"));				// true		
		System.out.println(isRealLiteralPart1("0.0E-12"));			// true		
		System.out.println(isRealLiteralPart1(".-12"));				// false
		System.out.println(isRealLiteralPart1(".E"));               // false

		System.out.println(isEmailAddress("johndoe@domainsample.com"));		// true		
		System.out.println(isEmailAddress("john.doe@domainsample.net"));	// true		
		System.out.println(isEmailAddress("john.doe43@domainsample.co.uk"));// true		
		System.out.println(isEmailAddress("@domainsample.com"));		// false		
		System.out.println(isEmailAddress("johndoedomainsample.com"));	// false		
		System.out.println(isEmailAddress("john.doe@.net"));			// false		
		System.out.println(isEmailAddress("john.doe43@domainsample"));	// false		
	}
}
