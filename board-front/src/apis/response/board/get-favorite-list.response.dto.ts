import { FavoriteListItem } from "types/interface";
import ResponseDto from "../response.dto";

export default interface GetFavoriteLiseResponseDto extends ResponseDto {
    favoriteList: FavoriteListItem[]
}