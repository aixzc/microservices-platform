/**
 * views personal
 */
declare type PersonalInfo = {
	newsInfoList:  NewInfo[]
	personalForm: {
		id: bigint;
		username: string;
		nickname: string;
		email: string;
		mobile: string;
		sex: bigint;
		headImgUrl: string;
	};
};

type NewInfo = {
	title: string;
	date: string;
	link: string;
};

