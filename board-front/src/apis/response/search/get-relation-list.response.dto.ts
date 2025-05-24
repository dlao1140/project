import ResponseDto from "../response.dto";

export default interface GetRelationListResponoseDto extends ResponseDto{
    relativeWordList: string[];
}